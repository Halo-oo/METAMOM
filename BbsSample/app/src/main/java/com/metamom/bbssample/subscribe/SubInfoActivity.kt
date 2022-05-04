package com.metamom.bbssample.subscribe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.metamom.bbssample.MainButtonActivity
import com.metamom.bbssample.R
import com.metamom.bbssample.subsingleton.MemberSingleton

class SubInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_info)

        /* 액션바 설정 */
        setSupportActionBar(findViewById(R.id.subInfo_toolbar))
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        findViewById<Toolbar>(R.id.subInfo_toolbar).setNavigationOnClickListener {
            onBackPressed()
        }

        val idTxt = findViewById<TextView>(R.id.subInfo_IdTxt)
        val typeTxt = findViewById<TextView>(R.id.subInfo_TypeTxt)
        val periodTxt = findViewById<TextView>(R.id.subInfo_PeriodTxt)
        val startdayTxt = findViewById<TextView>(R.id.subInfo_StartDayTxt)

        val morningCheck = findViewById<CheckBox>(R.id.subInfo_MorningChkBox)
        val lunchCheck = findViewById<CheckBox>(R.id.subInfo_LunchChkBox)
        val dinnerCheck = findViewById<CheckBox>(R.id.subInfo_DinnerChkBox)
        val snackCheck = findViewById<CheckBox>(R.id.subInfo_SnackChkBox)

        /* #21# 구독 회원정보 가져오기
        *  case_1) 회원정보 가져오기 실패 시 알림창 & main Button 페이지로 이동
        *  case_2) 회원정보 드로잉 */
        var subInfo = SubscribeDao.getInstance().getSubInfo(MemberSingleton.id.toString())

        if (subInfo != null){                                           // case_2)
            SubscribeDao.subUser = subInfo

            idTxt.text = subInfo.subId.toString()
            if (subInfo.subType.toString() == "0"){
                typeTxt.text = "다이어트"
            } else if (subInfo.subType.toString() == "1"){
                typeTxt.text = "운동"
            }

            periodTxt.text = "${subInfo.subPeriod.toString()}개월"
            startdayTxt.text = subInfo.subStartday.toString()

            if (subInfo.subMorning == 1){
                morningCheck.isChecked = true
            }
            if (subInfo.subLunch == 1){
                lunchCheck.isChecked = true
            }
            if (subInfo.subDinner == 1){
                dinnerCheck.isChecked = true
            }
            if (subInfo.subSnack == 1){
                snackCheck.isChecked = true
            }
        }
        else {                                                          // case_1)
            Toast.makeText(this, "죄송합니다. 다시 시도해주세요!", Toast.LENGTH_LONG).show()

            val i = Intent(this, MainButtonActivity::class.java)
            startActivity(i)
        }
    }
}