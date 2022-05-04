package com.metamom.bbssample.subscribe

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.metamom.bbssample.MainButtonActivity
import com.metamom.bbssample.R
import com.metamom.bbssample.databinding.ActivitySubAddBinding
import com.metamom.bbssample.subsingleton.MemberSingleton
import com.metamom.bbssample.subsingleton.SubAddSingleton
import com.metamom.bbssample.subsingleton.SubPurchaseSingleton

class SubAddActivity : AppCompatActivity() {

    /* #21# 구글 인앱 결제 사용을 위하여 binding 사용 */
    private lateinit var binding: ActivitySubAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* #21# 구글 인앱 결제 사용을 위하여 binding 사용 */
        binding = ActivitySubAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_sub_add)

        /* 액션바 설정 */
        setSupportActionBar(findViewById(R.id.subAdd_toolbar))
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        findViewById<Toolbar>(R.id.subAdd_toolbar).setNavigationOnClickListener {
            onBackPressed()
        }

        // 기존의 구독신청 데이터값 초기화
        SubAddSingleton.subType = null
        SubAddSingleton.subPeriod = null
        SubAddSingleton.subMorning = 0
        SubAddSingleton.subLunch = 0
        SubAddSingleton.subDinner = 0
        SubAddSingleton.subSnack = 0

        /* #21# Spinner 세팅 + Spinner 선택 값 SubAddSingleton에 저장 */
        setupSpinner()                                  // Spinner 세팅
        setupSpinnerHandler()                           // Spinner 선택 시 이벤트

        /* #21# 구독신청 _구글 인앱 결제 */
        val subAddBtn = findViewById<Button>(R.id.subAdd_addBtn)
        with (binding) {
            subAddBtn.setOnClickListener {
                // 선택한 개월(1, 3, 5) Singleton에 저장
                SubPurchaseSingleton.subPeriod = SubAddSingleton.subPeriod

                // 선택한 구독 시간 Singleton에 저장
                timeCheck()

                // 구독신청에 필요한 데이터 유효성 검사
                Log.d("SubAddActivity", "#21# SubAddActivity 선택한 구독신청을 위한 데이터 > ${SubAddSingleton.toString()}")
                if (subChoiceCheck() == true){
                    /* !! 구글 인앱 결제를 위한 Activity로 이동 */
                    val i = Intent(this@SubAddActivity, SubPurchaseActivity::class.java)
                    startActivity(i)
                }
            }
        }

        /* #21# 구독신청 _결제 API */
        val subAPIBtn = findViewById<Button>(R.id.subAdd_payAPIBtn)
        subAPIBtn.setOnClickListener {
            // 선택한 개월(1, 3, 5) Singleton에 저장
            SubPurchaseSingleton.subPeriod = SubAddSingleton.subPeriod

            // 선택한 구독 시간 Singleton에 저장
            timeCheck()

            // 구독신청에 필요한 데이터 유효성 검사
            Log.d("SubAddActivity", "#21# SubAddActivity 선택한 구독신청을 위한 데이터 > ${SubAddSingleton.toString()}")
            if (subChoiceCheck() == true){
                /* !! 카드 결제를 위한 Activity로 이동 */
                startActivity(Intent(this@SubAddActivity, SubAPIPurchaseActivity::class.java))
            }
        }

    }






    /* Spinner 세팅 */
    fun setupSpinner() {

        // 1) 유형 Spinner
        val typeData = resources.getStringArray(R.array.subtype)                                    // subadd_array.xml 내 subtype 배열
        val typeAdapter = ArrayAdapter(this, R.layout.subadd_item_spinner, typeData)        // typeAdapter 변수에 배열과 xml을 적용

        val TypeSpinner = findViewById<Spinner>(R.id.subAdd_TypeSpinn)
        TypeSpinner.adapter = typeAdapter

        // 2) 기간 Spinner
        val periodData = resources.getStringArray(R.array.subperiod)
        val periodAdapter = ArrayAdapter(this, R.layout.subadd_item_spinner, periodData)

        val periodSpinner = findViewById<Spinner>(R.id.subAdd_PeriodSpinn)
        periodSpinner.adapter = periodAdapter
    }


    /* Spinner 선택 시 이벤트 */
    fun setupSpinnerHandler() {

        // 1) 유형 Spinner
        val TypeSpinner = findViewById<Spinner>(R.id.subAdd_TypeSpinn)
        TypeSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            // 선택되었을 때
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("SubAddActivity", "#21# Type Spinner 선택한 값: ${TypeSpinner.getItemAtPosition(position)}")      // getItemAtPosition == position에 해당되는 item 가져오기

                if (TypeSpinner.getItemAtPosition(position).toString() == "다이어트") {
                    SubAddSingleton.subType = 0
                } else if (TypeSpinner.getItemAtPosition(position).toString() == "운동") {
                    SubAddSingleton.subType = 1
                } else if (TypeSpinner.getItemAtPosition(position).toString() == "유형 선택") {
                    SubAddSingleton.subType = null
                } else {
                    Toast.makeText(this@SubAddActivity, "관리자에게 문의하여 주시길 바랍니다. 죄송합니다", Toast.LENGTH_LONG).show()
                    Log.d("SubAddActivity", "#21# 구독 유형 Spinner 선택 Error")
                }
            }

            // 아무것도 선택되지 않았을 때
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // 2) 기간 Spinner
        val PeriodSpinner = findViewById<Spinner>(R.id.subAdd_PeriodSpinn)
        PeriodSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            // 선택되었을 때
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("SubAddActivity", "#21# Period Spinner 선택한 값: ${PeriodSpinner.getItemAtPosition(position)}")

                if (PeriodSpinner.getItemAtPosition(position).toString() == "1개월[1,000원]") {
                    SubAddSingleton.subPeriod = 1
                } else if (PeriodSpinner.getItemAtPosition(position).toString() == "3개월[3,000원]") {
                    SubAddSingleton.subPeriod = 3
                } else if (PeriodSpinner.getItemAtPosition(position).toString() == "5개월[5,000원]") {
                    SubAddSingleton.subPeriod = 5
                } else if (PeriodSpinner.getItemAtPosition(position).toString() == "기간 선택") {
                    SubAddSingleton.subPeriod = null
                } else {
                    Toast.makeText(this@SubAddActivity, "관리자에게 문의하여 주시길 바랍니다. 죄송합니다", Toast.LENGTH_LONG).show()
                    Log.d("SubAddActivity", "#21# 구독 기간 Spinner 선택 Error")
                }
            }

            // 아무것도 선택되지 않았을 때
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    /* 3) 구독 시간 선택값 Singleton에 저장  */
    fun timeCheck() {
        val morningCheckBox = findViewById<CheckBox>(R.id.subAdd_morningCheck)
        val lunchCheckBox = findViewById<CheckBox>(R.id.subAdd_lunchCheck)
        val dinnerCheckBox = findViewById<CheckBox>(R.id.subAdd_dinnerCheck)
        val snackCheckBox = findViewById<CheckBox>(R.id.subAdd_snackCheck)
        if(morningCheckBox.isChecked) {
            SubAddSingleton.subMorning = 1
        } else {
            SubAddSingleton.subMorning = 0
        }
        if (lunchCheckBox.isChecked) {
            SubAddSingleton.subLunch = 1
        } else {
            SubAddSingleton.subLunch = 0
        }
        if (dinnerCheckBox.isChecked) {
            SubAddSingleton.subDinner = 1
        } else {
            SubAddSingleton.subDinner = 0
        }
        if (snackCheckBox.isChecked) {
            SubAddSingleton.subSnack = 1
        } else {
            SubAddSingleton.subSnack = 0
        }
    }

    /* 구독신청에 필요한 데이터 유효성 검증 */
    fun subChoiceCheck() :Boolean {
        // 유형
        if (SubAddSingleton.subType == null) {
            Toast.makeText(this, "구독 유형을 선택해주세요!", Toast.LENGTH_LONG).show()
            return false
        }
        // 기간
        if (SubAddSingleton.subPeriod == null) {
            Toast.makeText(this, "구독 기간을 선택해주세요!", Toast.LENGTH_LONG).show()
            return false
        }
        // 시간
        if (SubAddSingleton.subMorning == 0 && SubAddSingleton.subLunch == 0 && SubAddSingleton.subDinner == 0 && SubAddSingleton.subSnack == 0) {
            Toast.makeText(this, "구독 시간을 선택해주세요!", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}