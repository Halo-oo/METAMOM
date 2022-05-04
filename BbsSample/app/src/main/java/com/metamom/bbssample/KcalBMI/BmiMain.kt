package com.metamom.bbssample.KcalBMI

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.metamom.bbssample.MainButtonActivity
import com.metamom.bbssample.MemberDao
import com.metamom.bbssample.R
import com.metamom.bbssample.databinding.ActivityBmiMainBinding
import com.metamom.bbssample.subsingleton.MemberSingleton
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class BmiMain : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"    // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"    // US Unit View
    }
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private val binding by lazy{ ActivityBmiMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바 설정
        setSupportActionBar(binding.toolbarBmiActivity)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI 계산기"
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }

        }

        binding.btnCalculateUnits.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE
        binding.tilUsMetricUnitWeight.visibility = View.GONE
        binding.tilMetricUsUnitHeightFeet.visibility = View.GONE
        binding.tilMetricUsUnitHeightInch.visibility = View.GONE

        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilMetricUnitHeight.visibility = View.INVISIBLE
        binding.tilUsMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUsUnitHeightFeet.visibility = View.VISIBLE
        binding.tilMetricUsUnitHeightInch.visibility = View.VISIBLE

        binding.etUsMetricUnitWeight.text!!.clear()
        binding.etUsMetricUnitHeightFeet.text!!.clear()
        binding.etUsMetricUnitHeightInch.text!!.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi:Float){

        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "매우 극심한 저체중입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_red01))
            bmiDescription = "회원님의 건강을 위해 메타몸의 건강한 삼시세끼를 드셔보세요!"
        } else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "심각한 저체중입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_red01))
            bmiDescription = "회원님의 건강을 위해 메타몸의 건강한 삼시세끼를 드셔보세요!"
        } else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "체중 미달입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_purple01))
            bmiDescription = "회원님의 건강을 위해 메타몸의 건강한 식단과 함께하세요!"
        } else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "정상입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_green01))
            bmiDescription = "정상 체중입니다, 메타몸과 함께 꾸준히 관리해봐요!"
        } else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "과체중입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_purple01))
            bmiDescription = "회원님의 건강을 위해 메타몸의 건강한 식단과 함께 운동을 시작하세요!"
        } else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "중도 비만입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_purple01))
            bmiDescription = "회원님의 건강을 위해 메타몸의 건강한 식단과 함께 운동을 시작하세요!"
        } else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "고도 비만입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_red01))
            bmiDescription = "회원님의 건강이 위험할지도 모릅니다, 메타몸의 건강한 식단과 함께 운동을 시작하세요!"
        } else {
            bmiLabel = "심각한 고도 비만입니다"
            binding.tvBMIType.setTextColor(ContextCompat.getColor(applicationContext, R.color.meta_red01))
            bmiDescription = "회원님의 건강이 위험할지도 모릅니다, 메타몸의 건강한 식단과 함께 운동을 시작하세요!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.llDisplayBMIResult.visibility = View.VISIBLE
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }

    // 입력창이 비었는지 확인(Metric)
    private fun validateMetricUnits():Boolean{
        var isValid = true  // 초기화

        if(binding.etMetricUnitWeight.text.toString().isEmpty()){
            isValid = false
        }else if(binding.etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue : Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue : Float = binding.etMetricUnitWeight.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue*heightValue) * 100

                displayBMIResult(bmi)

            }else{
                Toast.makeText(this@BmiMain,
                    "유효한 값을 입력해 주세요.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            if(validateUsUnits()){
                val usUnitHeightValueFeet: String =
                    binding.etUsMetricUnitHeightFeet.text.toString()
                val usUnitHeightValueInch: String =
                    binding.etUsMetricUnitHeightInch.text.toString()
                val usUnitWeightValue: Float = binding.etUsMetricUnitWeight
                    .text.toString().toFloat()

                // 인치 계산
                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi)

            }else{
                Toast.makeText(this@BmiMain,
                    "유효한 값을 입력해 주세요.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // 입력창이 비었는지 확인(US)
    private fun validateUsUnits():Boolean{
        var isValid = true  // 초기화

        when {
            binding.etUsMetricUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsMetricUnitHeightFeet.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsMetricUnitHeightInch.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }

}