package com.metamom.bbssample.subscribe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.metamom.bbssample.MainButtonActivity
import com.metamom.bbssample.R
import com.metamom.bbssample.databinding.ActivitySubPurchaseBinding
import com.metamom.bbssample.subsingleton.MemberSingleton
import com.metamom.bbssample.subsingleton.SubAddSingleton
import com.metamom.bbssample.subsingleton.SubPurchaseSingleton

/* #21# 구글 인앱 결제 Activity */
// - 생성한 BillingModule을 생성하고, onBillingModuleIsReady()에서 상품정보를 불러와서 저장한다.
class SubPurchaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubPurchaseBinding
    private lateinit var bm: BillingModule

    private var mSkuDetails = listOf<SkuDetails>()
        set(value) {
            field = value
            Log.d("SubPurchaseActivity", "#21# SubPurchaseActivity 결제 상품정보 가져오기 실행")
            setSkuDetailsView()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_sub_purchase)

        /* 액션바 설정 */
        setSupportActionBar(binding.subPurchaseToolbar)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.subPurchaseToolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        bm = BillingModule(this, lifecycleScope, object: BillingModule.Callback {
            override fun onBillingModulesIsReady() {
                when (SubPurchaseSingleton.subPeriod) {
                    1 -> {
                        bm.querySkuDetail(BillingClient.SkuType.INAPP, Sku.TODAY_MEAL_1) { skuDetails ->
                            mSkuDetails = skuDetails
                        }
                    }
                    3 -> {
                        bm.querySkuDetail(BillingClient.SkuType.INAPP, Sku.TODAY_MEAL_3) { skuDetails ->
                            mSkuDetails = skuDetails
                        }
                    }
                    5 -> {
                        bm.querySkuDetail(BillingClient.SkuType.INAPP, Sku.TODAY_MEAL_5) { skuDetails ->
                            mSkuDetails = skuDetails
                        }
                    }
                    else -> {
                        Log.d("SubPurchaseActivity", "#21# SubPurchaseActivity 구글 인앱 구매 실행 Error > 구독 신청한 개월 수 Error")
                    }
                }

                /*bm.querySkuDetail(BillingClient.SkuType.INAPP,*//*Sku.TODAY_MEAL_1, Sku.TODAY_MEAL_3, Sku.TODAY_MEAL_5*//*) { skuDetails ->
                    mSkuDetails = skuDetails
                }*/
            }

            override fun onSuccess(purchase: Purchase) {
                Log.d("SubPurchaseActivity", "#21# SubPurchaseActivity 구매 성공")

                if (SubAddSingleton.subType != null && SubAddSingleton.subType != null){
                    val addResult = SubscribeDao.getInstance().subAdd(SubscribeDto(MemberSingleton.id.toString(),
                        SubAddSingleton.subType!!,
                        SubAddSingleton.subPeriod!!,
                        SubAddSingleton.subMorning,
                        SubAddSingleton.subLunch,
                        SubAddSingleton.subDinner,
                        SubAddSingleton.subSnack,
                        "", ""))
                    Log.d("SubPurchaseActivity", "#21# SubPurchaseActivity 구독 신청 Back으로부터 전달받은 결과값 > ${addResult.toString()}")

                    if (addResult == "Success"){
                        val builder = AlertDialog.Builder(this@SubPurchaseActivity)
                        builder.setTitle("구독 신청")
                        builder.setMessage("구독이 신청되었습니다! 감사합니다 😌")
                        builder.show()

                        /* !!! 구독 신청 후 MemberSingleton값 수정 */
                        MemberSingleton.subscribe = "1"

                        val i = Intent(this@SubPurchaseActivity, MainButtonActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this@SubPurchaseActivity, "죄송합니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(errorCode: Int) {
                when (errorCode) {
                    BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                        Toast.makeText(this@SubPurchaseActivity, "이미 구입한 상품입니다.", Toast.LENGTH_LONG).show()
                    }
                    BillingClient.BillingResponseCode.USER_CANCELED -> {
                        Toast.makeText(this@SubPurchaseActivity, "구매를 취소하였습니다.", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.d("SubPurchaseActivity", "#21# SubPurchaseActivity 구매 Error 발생 > Error 코드: $errorCode")
                    }
                }
            }
        })
        setClickListeners()
    }

    fun setClickListeners() {
        with (binding) {
            subPurchasePurchaseBtn.setOnClickListener {
                when (SubPurchaseSingleton.subPeriod) {
                    1 -> {
                        mSkuDetails.find { it.sku == Sku.TODAY_MEAL_1 }?.let { skuDetails ->
                            bm.purchase(skuDetails)
                        } ?: also {
                            Toast.makeText(this@SubPurchaseActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
                    3 -> {
                        mSkuDetails.find { it.sku == Sku.TODAY_MEAL_3 }?.let { skuDetails ->
                            bm.purchase(skuDetails)
                        } ?: also {
                            Toast.makeText(this@SubPurchaseActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
                    5 -> {
                        mSkuDetails.find { it.sku == Sku.TODAY_MEAL_5 }?.let { skuDetails ->
                            bm.purchase(skuDetails)
                        } ?: also {
                            Toast.makeText(this@SubPurchaseActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
                    else -> {
                        Log.d("SubPurchaseActivity", "#21# SubPurchaseActivity 구글 인앱 구매 실행 Error > 신청한 개월 수에 대한 결제 Error")
                    }
                }

                /*mSkuDetails.find { it.sku == Sku.TODAY_MEAL_1 }?.let { skuDetails ->
                    bm.purchase(skuDetails)
                } ?: also {
                    Toast.makeText(this@SubPurchaseActivity, "상품을 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                }*/
            }
        }
    }

    /* 상품정보 표시 */
    // - getPrice _화폐기호를 포함한 포맷으로 지정된 가격을 반환
    // - getPriceAmountMicros _백만분의 일(micro) 단위의 가격을 반환, Long 형식 | ex) $7.99 > 7990000
    // - getPriceCurrencyCode _화폐코드 반환 | ex) "USD", "KRW"
    // - getSku: 고유 아이디인 SKU 코드 반환 | 구글 플레이 콘솔에서 생성하였던 상품 ID > "today_meal_1", "today_meal_3", "today_meal_5"
    fun setSkuDetailsView() {
        /*val builder = StringBuilder()
        for (skuDetail in mSkuDetails) {
            builder.append("<${skuDetail.title}\n")
            builder.append(skuDetail.price)
            builder.append("\n=========================\n\n")
        }
        binding.subPurchaseTvSkuTxt.text = builder*/

        // 유형
        var type :String? = null
        if (SubAddSingleton.subType == 0){
            type = "다이어트"
        } else if (SubAddSingleton.subType == 1) {
            type = "운동"
        }
        binding.subPurchaseTypeTxt.text = type

        // 기간
        binding.subPurchasePeriodTxt.text = "${SubAddSingleton.subPeriod}개월"

        // 가격
        val builder = StringBuilder()
        for (skuDetail in mSkuDetails) {
            builder.append(skuDetail.price)
        }
        binding.subPurchaseCostTxt.text = builder

        // 시간
        var timeTxtList = mutableListOf<String>()
        if (SubAddSingleton.subMorning == 1) timeTxtList.add("아침")
        if (SubAddSingleton.subLunch == 1) timeTxtList.add("점심")
        if (SubAddSingleton.subDinner == 1) timeTxtList.add("저녁")
        if (SubAddSingleton.subSnack == 1) timeTxtList.add("간식")
        binding.subPurchaseTimeTxt.text = "$timeTxtList"

        // 상품설명
        binding.subPurchaseExplainTxt.text = "'오늘의 식단' 서비스는 ${SubAddSingleton.subPeriod}개월 동안 신청하신 $type 유형의 식단을 제공하는 서비스 입니다. \n" +
                "가격은 ${SubAddSingleton.subPeriod}개월에 ${builder}원 이며, 결제를 원하지 않으시다면 이전화면으로 돌아가주세요. \n감사합니다"
    }

    override fun onResume() {
        super.onResume()
        bm.onResume(BillingClient.SkuType.INAPP)
    }

    object Sku {
        const val TODAY_MEAL_1 = "today_meal_1"
        const val TODAY_MEAL_3 = "today_meal_3"
        const val TODAY_MEAL_5 = "today_meal_5"
    }


}