package com.metamom.bbssample.subscribe

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.android.billingclient.api.*
import com.metamom.bbssample.subsingleton.SubPurchaseSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import javax.security.auth.callback.Callback
import kotlin.math.log

// #21# 구글 인앱 결제모듈
// 구글에서 인앱 결제를 위한 라이브러리 BillingClent를 제공한다. > 이를 더욱 편하게 쓰기 위하여 BillingModule 이라는 Wrapper 클래스를 생성하여 사용
class BillingModule(private val activity: Activity, private val lifeCycleScope: LifecycleCoroutineScope, private val callback: Callback) :
    PurchaseHistoryResponseListener {

    interface Callback {
        // onBillingModulesIsReady _BillingClient가 연결에 성공하여 모듈을 사용할 준비가 되었음을 알리기 위함 (이것이 호출되기 전에는 아무런 기능을 사용할 수 없다.)
        fun onBillingModulesIsReady()

        // onSuccess _구매가 성공했을 때 호출되며, purchase는 구매한 정보
        fun onSuccess(purchase: Purchase)

        // onFailure _구매가 실패했을 때 호출되며, BillingResponseCode가 넘겨진다.
        fun onFailure(errorCode: Int)
    }


    /* 1) BillingClient 초기화 _(purchasesUpdatedListener) */
    // - 구매가 이루어지면 PurchasesUpdatedListener 에서 CallBack을 수신 받는다.
    // - !! 구매가 완료되면 구매 확인(acknowledge) 처리를 해야한다. 안하면 환불됨

    // 구매관련 업데이트 수신
    val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        when {
            billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null -> {
                // 구매완료 확인 처리 > 3일 이내 구매완료 확인 처리를 하지 않으면 자동으로 환불된다.
                for (purchase in purchases) {
                    confirmPurchase(purchase)
                }
            }
            else -> {
                // 구매 실패
                Log.d("BillingModule", "#21# BillingModule 구매 실패 & responseCode 확인 [${billingResult.responseCode}]")

                if (billingResult.responseCode == 7){
                    getAllPurchaseItem()
                    Log.d("BillingModule", "#21# BillingModule getAllPurchaseItem() 함수 실행 > responseCode ITEM_ALREADY_OWNED 해결을 위하여")
                }
                
                callback.onFailure(billingResult.responseCode)
            }
        }
    }

    var billingClient: BillingClient = BillingClient.newBuilder(activity)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()


    /* 2) GooglePlay 와 연결 */
    init {
        billingClient.startConnection(object: BillingClientStateListener {

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                Log.d("BillingModule", "#21# BillingModule _GooglePlay 와 연결완료 > billingResult.responseCode 확인: ${billingResult.responseCode}")

                // Response가 OK로 떨어지면 그때 생성하였던 CallBack으로 사용 가능하다고 알려주게 된다. > 이 시점 이후부터 상품정보 불러오기, 구매 등이 가능
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // 이때, billingClient가 활성화 된다.
                    callback.onBillingModulesIsReady()
                }
                else {
                    callback.onFailure(billingResult.responseCode)
                }
            }

            // GooglePlay와 연결이 끊어졌을 경우 > 여기에 연결을 재시도하는 로직이 들어갈 수 있다.
            override fun onBillingServiceDisconnected() {
                Log.d("BillingModule", "#21# BillingModule _GooglePlay 와의 연결이 끊어짐 Disconnected")
            }
        })
    }

    /* 3) 상품정보 불러오기 */
    // - !! 해당 code는 Coroutine를 사용한 버전 (사용하지 않는다면 Async 버전으로 작성하기)
    // - APK를 만들어서, 구글 플레이 내부 테스트에 배포 > 인앱 상품 등록 후 진행 필요 (cuz, 상품정보가 뜨지 않는 Error 발생 가능성 있음)
    // - 원하는 sku id를 가지고 있는 상품 정보를 가져온다.
    // - @param sku sku 목록
    // - @param resultBlock sku 상품정보 CallBack
    // - 원하는 상품정보를 불러오기 위하여 querySkuDetails를 호출한다.
    // - 상품형태: SkuType.INAPP == 인앱결제 상품, SkuType.SUBS == 구독상품
    // - 받아온 SkuDetail에는 이름, 설명, 가격정보, Sku 코드 등 결제를 위한 다양한 정보가 들어있다. (받아온 정보를 Activity에서 상품정보 표시)
    fun querySkuDetail(type: String = BillingClient.SkuType.INAPP, vararg sku: String, resultBlock: (List<SkuDetails>) -> Unit = {} ) {

        SkuDetailsParams.newBuilder().apply {
            // 인앱, 정기결제 유형 중 택 (SkuType.INAPP, SkuType.SUBS)
            setSkusList(sku.asList()).setType(type)

            // 비동기적으로 상품정보를 가져온다.
            lifeCycleScope.launch(Dispatchers.IO) {
                val skuDetailsResult = billingClient.querySkuDetails(build())
                withContext(Dispatchers.Main) {
                    resultBlock(skuDetailsResult.skuDetailsList?: emptyList())
                }
            }
        }
    }


    /* 4) 구매 시작하기 */
    // - purchase() 메소드를 호출하고 SkuDetail을 넘겨주게되면 해당 상품에 대한 구매절차를 시작한다.
    // - @param skuDetail 구매하고자 하는 항목, querySkuDetail()을 통해 획득한 SkuDetail
    fun purchase(skuDetails: SkuDetails) {

        val flowParams = BillingFlowParams.newBuilder().apply {
            setSkuDetails(skuDetails)
        }.build()

        // 구매 절차 시작 > OK일 시 제대로 동작한 것
        val responseCode = billingClient.launchBillingFlow(activity, flowParams).responseCode
        Log.d("BillingModule", "#21# BillingModule purchase() 구매 절차 시작 and responseCode 결과 > ${responseCode.toString()}")

        if (responseCode != BillingClient.BillingResponseCode.OK) {
            Log.d("BillingModule", "#21# BillingModule purchase() 구매절차 시작 시 Error 발생")
            callback.onFailure(responseCode)
        }
        // 이후부터는 purchasesUpdatedListener 를 거치게 된다.
    }


    /* 5) 소비(consume)와 구매 확인하기 (acknowledge) _(purchasesUpdatedListener) */
    // - 사용자가 반복적으로 구매할 수 있는 상품에 대해서는 소비(consume)처리를 해야 재구매가 가능 <-> 안하면 계정당 1회 구매만 가능
    // - 또한, 결제 완료 후 (puchaseState == PurchaseState.PURCHASED) 구매 확인이 되지 않은 경우(!purchase.isAcknowledged)에도 자동 환불되기 때문에 이를 막기 위하여 구매 확인 절차 필요하다.
    // - 이에 따라 구매 확인처리를 하는 confirmPurchase 메소드 생성
    // - BillingClient.consumePurchase() == 소비,  BillingClient.acknowledgePurchase() == 구매 확인을 하는 부분

    // 소비되어야 하는 Sku 목록 작성
    //val consumableSkus = setOf(SubPurchaseActivity.Sku.TODAY_MEAL_1/*, SubPurchaseActivity.Sku.TODAY_MEAL_3, SubPurchaseActivity.Sku.TODAY_MEAL_5*/)
    val consumableSkus = setOf(SubPurchaseActivity.Sku.TODAY_MEAL_1, SubPurchaseActivity.Sku.TODAY_MEAL_3, SubPurchaseActivity.Sku.TODAY_MEAL_5)


    // 구매 확인 처리
    // - @param purchase 확인처리할 아이템의 구매정보
    fun confirmPurchase(purchase: Purchase) {
        when {
            consumableSkus.contains<Serializable>(purchase/*.sku*/.skus) -> {
                // 소비성 구매는 consume을 해줘야 한다.
                val consumeParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                lifeCycleScope.launch(Dispatchers.IO) {
                    val result = billingClient.consumePurchase(consumeParams)
                    withContext(Dispatchers.Main) {
                        if (result.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            callback.onSuccess(purchase)
                        }
                    }
                }
            }
            purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged -> {
                // 구매는 완료되었으나 확인이 되어 있지 않다면 구매확인 처리 하기
                val ackPurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)

                lifeCycleScope.launch(Dispatchers.IO) {
                    val result = billingClient.acknowledgePurchase(ackPurchaseParams.build())
                    withContext(Dispatchers.Main) {
                        if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                            callback.onSuccess(purchase)
                        } else {
                            callback.onFailure(result.responseCode)
                        }
                    }
                }
            }
        }
    }


    /* 6) 예외상황 건에 대한 확인처리 */
    // - 구매 시 네트워크 오류 등의 이유로 구매확인을 놓친 경우를 대비하여 놓은 구매건이 있으면 확인을 진행하는 메소드 생성
    // - @param type BillingClient.SkuType.INAPP 또는 BillingClient.SkuType.SUBS
    fun onResume(type: String) {
        if (billingClient.isReady) {
            billingClient.queryPurchases(type).purchasesList?.let { purchasesList ->
                for (purchase in purchasesList) {
                    if (!purchase.isAcknowledged && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        confirmPurchase(purchase)
                    }
                }
            }
        }
    }


    /* 7) 구매여부 체크 */
    // - !! 소비성 구매가 아닌 항목에 한정
    // - 사용자의 구매정보를 가져와서 해당 sku가 정상적으로 구매 완료된 상태라면 true 반환, 기록이 없다면 false 반환
    // - @param sku
    /*fun checkPurchased(sku: String, resultBlock: (purchased: Boolean) -> Unit) {
        billingClient.queryPurchases(BillingClient.SkuType.INAPP).purchasesList?.let { purchaseList ->
            for (purchase in purchaseList) {
                if (purchase.sku == sku && purchase.isPurchaseConfirmed()) {
                    return resultBlock(true)
                }
            }
            return resultBlock(false)
        }
    } */

    // 구매확인 검사 Extension
    fun Purchase.isPurchaseConfirmed(): Boolean {
        return this.isAcknowledged && this.purchaseState == Purchase.PurchaseState.PURCHASED
    }


    /* responseCode 7: 이미 구매한 상품, ITEM_ALREADY_OWNED Error 대처 */
    // - 구매하였던 이력이 있다면 구매 이력을 이용하여 소비 하는 방법을 통해 Error 대처
    // - 구매처리 후 소비처리가 정상적으로 이루어지지 않은 경우 해당 Error가 발생할 수 있음
    // - 이에 따라, 구매이력을 모두 찾아 모두 소비 후 다시 구매 진행
    fun getAllPurchaseItem() {
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, this)
    }

    // 최근 구매한 아이템을 알고자 할 때 사용
    // getAllPurchaseItem 의 this
    override fun onPurchaseHistoryResponse(billingResult: BillingResult, purchaseHistoryList: MutableList<PurchaseHistoryRecord>?) {
        Log.e("BillingModule", "#21# BillingModule getAllPurchaseItem ${billingResult.debugMessage}")

        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK){
            if (!purchaseHistoryList.isNullOrEmpty()) {
                // 가장 최근에 구매된 아이템
                purchaseHistoryList.forEach{
                    Log.e("BillingModule", "#21# BillingModule 가장 최근에 구매된 아이템: ${it.quantity}")

                    // 소비되지 않은 아이템이 있다면 소비처리 하기
                    var consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(it.purchaseToken)
                        .build()

                    billingClient.consumeAsync(consumeParams) { billingResult, _ ->
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            Log.e("BillingModule", "#21# BillingModule onPurchaseHistoryResponse: ${billingResult.debugMessage}")
                            //log(msg="${billingResult.debugMessage}")
                            Log.e("BillingModule", "#21# BillingModule msg=${billingResult.debugMessage}")
                        }
                    }
                }
            }
        }
    }

}