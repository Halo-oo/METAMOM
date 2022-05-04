package com.metamom.bbssample.subscribe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.metamom.bbssample.MainButtonActivity
import com.metamom.bbssample.R
import com.metamom.bbssample.sns.SnsDao
import com.metamom.bbssample.subsingleton.MemberSingleton
import com.metamom.bbssample.subsingleton.SubAddSingleton
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import org.w3c.dom.Text


/* #21# 결제 API __BootPay API 사용*/
class SubAPIPurchaseActivity : AppCompatActivity() {

    // Bootpay android application id값 설정, 결제 & 통계를 위해 필수 (https://admin.bootpay.co.kr/install/key)
    val applicationId = "624f90e22701800020f68a51"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_api_purchase)

        /* 액션바 설정 */
        setSupportActionBar(findViewById(R.id.subAPI_toolbar))
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        findViewById<Toolbar>(R.id.subAPI_toolbar).setNavigationOnClickListener {
            onBackPressed()
        }

        // 선택한 상품설명
        setInfo()

        // Bootpay 초기설정
        BootpayAnalytics.init(this, applicationId)

        findViewById<Button>(R.id.subAPI_purchaseBtn).setOnClickListener {
            goBootpayRequest()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // 메뉴를 확장합니다. 수행 표시줄이 있는 경우 항목이 추가됩니다.
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        // The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    // BootPay
    fun goBootpayRequest() {
        // 현재 로그인한 사용자의 정보 가져오기
        val userInfo = SnsDao.getInstance().snsGetMember(MemberSingleton.id.toString())
        Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API 사용을 위하여 회원정보 가져온 값 > ${userInfo.toString()}")

        // 결제호출
        val bootUser = BootUser().setPhone(userInfo.phone)     // 구매자 전화번호
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))

        val stuck = 1 // 재고 있음

        // 선택한 제품유형
        var productName :String = ""
        if (SubAddSingleton.subType == 0) productName = "다이어트"
        else if (SubAddSingleton.subType == 1) productName = "운동"

        // 선택한 개월에 따른 가격
        var productPrice :Int = 0
        if (SubAddSingleton.subPeriod == 1) productPrice = 1000
        else if (SubAddSingleton.subPeriod == 3) productPrice = 3000
        else if (SubAddSingleton.subPeriod == 5) productPrice = 5000

        Bootpay.init(this)
            .setApplicationId(applicationId)    // 해당 프로젝트(android)의 application id값
            .setContext(this)
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setUX(UX.PG_DIALOG)
            .setPG(PG.INICIS)
            .setMethod(Method.CARD)
            .setName("$productName 식단 ${SubAddSingleton.subPeriod}개월")         // 결제 상품명
            .setOrderId("1234")                 // 결제 고유번호 expire_month
            .setPrice(productPrice)
            .addItem("$productName 식단 ${SubAddSingleton.subPeriod}개월", 1, "ITEM_CODE_SAMPLE_${MemberSingleton.id}", productPrice)  // 주문정보에 담길 상품정보, 통계를 위해 사용
            .addItem("주문정보에 담길 상품정보_2", 1, "ITEM_CODE_SAMPLE_2", 3000)
            .onConfirm { message -> // 결제 승인이 되기 전 호출되는 함수
                if (0 < stuck) Bootpay.confirm(message)     // 재고가 있을 경우
                else Bootpay.removePaymentWindow()          // 재고가 없을 경우 > 결제창 닫기
                Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API confirm > message: $message")
            }
            .onDone { message ->    // PG에서 거래 승인 이후에 호출되는 함수, 결제 완료 후 다음 결제 결과를 호출 할 수 있음
                Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API done > message: $message")

                // 구매성공 시 진행 code
                Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API 구매 성공")

                if (SubAddSingleton.subType != null && SubAddSingleton.subType != null){
                    val addResult = SubscribeDao.getInstance().subAdd(SubscribeDto(MemberSingleton.id.toString(),
                        SubAddSingleton.subType!!,
                        SubAddSingleton.subPeriod!!,
                        SubAddSingleton.subMorning,
                        SubAddSingleton.subLunch,
                        SubAddSingleton.subDinner,
                        SubAddSingleton.subSnack,
                        "", ""))
                    Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 구독 신청 Back으로부터 전달받은 결과값 > ${addResult.toString()}")

                    if (addResult == "Success"){
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("구독 신청")
                        builder.setMessage("구독이 신청되었습니다! 감사합니다 😌")
                        builder.show()

                        /* !!! 구독 신청 후 MemberSingleton값 수정 */
                        MemberSingleton.subscribe = "1"

                        val i = Intent(this, MainButtonActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this, "죄송합니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .onReady { message ->   // 가상계좌 발급 완료 시 호출되는 함수
                Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API ready > message: $message")
            }
            .onCancel { message ->  // 결제 진행 중 상요자가 취소 또는 닫기 버튼을 눌러 나온 경우
                Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API cancel > message: $message")
            }
            .onError { message ->   // 결제 진행 중 Error 발생
                Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API error > message: $message")
            }
            .onClose { message ->
                Log.d("SubAPIPurchaseActivity", "#21# SubAPIPurchaseActivity 결제 API close > message: $message")
            }
            .request()
    }

    /* 선택한 상품정보 출력 */
     fun setInfo() {
        // 유형
         var type :String? = null
         if (SubAddSingleton.subType == 0){
             type = "다이어트"
         } else if (SubAddSingleton.subType == 1) {
             type = "운동"
         }
        findViewById<TextView>(R.id.subAPI_typeTxt).text = type

        // 기간
        findViewById<TextView>(R.id.subAPI_periodTxt).text = "${SubAddSingleton.subPeriod}개월"

        // 가격
        var cost :Int = 0
        if (SubAddSingleton.subPeriod == 1) {
            findViewById<TextView>(R.id.subAPI_costTxt).text = "₩1,000"
            cost = 1000
        }
        else if (SubAddSingleton.subPeriod == 3) {
            findViewById<TextView>(R.id.subAPI_costTxt).text = "₩3,000"
            cost = 3000
        }
        else if (SubAddSingleton.subPeriod == 5) {
            findViewById<TextView>(R.id.subAPI_costTxt).text = "₩5,000"
            cost = 5000
        }

        // 시간
        var timeTxtList = mutableListOf<String>()
        if (SubAddSingleton.subMorning == 1) timeTxtList.add("아침")
        if (SubAddSingleton.subLunch == 1) timeTxtList.add("점심")
        if (SubAddSingleton.subDinner == 1) timeTxtList.add("저녁")
        if (SubAddSingleton.subSnack == 1) timeTxtList.add("간식")
        findViewById<TextView>(R.id.subAPI_timeTxt).text = "$timeTxtList"

        // 상품설명
        findViewById<TextView>(R.id.subAPI_plainTxt).text = "'오늘의 식단' 서비스는 ${SubAddSingleton.subPeriod}개월 동안 신청하신 $type 유형의 식단을 제공하는 서비스 입니다. \n" +
                "가격은 ${SubAddSingleton.subPeriod}개월에 ${cost}원 이며, 결제를 원하지 않으시다면 이전화면으로 돌아가주세요. \n감사합니다"
     }
}