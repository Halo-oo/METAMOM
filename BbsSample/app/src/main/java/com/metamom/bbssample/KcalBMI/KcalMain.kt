package com.metamom.bbssample.KcalBMI

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.metamom.bbssample.R
import com.metamom.bbssample.databinding.ActivityKcalMainBinding
import com.metamom.bbssample.databinding.ActivitySearchIdBinding

class KcalMain : AppCompatActivity() {
    private val binding by lazy { ActivityKcalMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //정보에 따른 칼로리 계산

        val genderRG = findViewById<RadioGroup>(R.id.genderRG)
        val runRG = findViewById<RadioGroup>(R.id.runRG)
        val heightEdit = findViewById<EditText>(R.id.heightEdit)
        val weightEdit = findViewById<EditText>(R.id.weightEdit)
        val ageEdit = findViewById<EditText>(R.id.ageEdit)
        val needKcal = findViewById<TextView>(R.id.needKcal)
        val kcalBtn = findViewById<Button>(R.id.kcalBtn)

        //기초대사량 = (10×(체중)+6.25×(키)-5.0×나이)+성별(남자 5, 여자 -151) * pal값

        //남녀 선택
        val gender = findViewById<TextView>(R.id.genderText)
        genderRG.setOnCheckedChangeListener { radioGroup, checkId ->
            when(checkId){
                R.id.maleRadio ->  gender.text = "남자"
                R.id.femaleRadio ->  gender.text = "여자"
            }
        }
        // 활동량 선택
        var value:Double = 0.0
        runRG.setOnCheckedChangeListener { radioGroup, checkId ->
            when(checkId){
                R.id.runBtn1 -> value = 1.3
                R.id.runBtn2 -> value = 1.5
                R.id.runBtn3 -> value = 1.7
                R.id.runBtn4 -> value = 2.0
                R.id.runBtn5 -> value = 2.4
            }
        }
        //var height:Int = heightEdit.text.toInt()
        //var weight:Int = weightEdit.text.toString().toInt()
        //var age:Int = ageEdit.text.toString().toInt()


        kcalBtn.setOnClickListener {

            var height: String = heightEdit.text.toString()
            var weight: String = weightEdit.text.toString()
            var age: String = ageEdit.getText().toString()

            //println("///////////// : " + weight.toDouble() * 24 * value)
            if(gender.text === "남자"){
                needKcal.text ="하루 소비 칼로리 "+(((10*weight.toInt())+(6.25*height.toInt())-(5*age.toInt())+5)*value).toString()+"Kcal"
            }
            if(gender.text === "여자"){
                needKcal.text ="하루 소비 칼로리 "+(((10*weight.toInt())+(6.25*height.toInt())-(5*age.toInt())-151)*value).toString()+"Kcal"
            }
        }

        // 액션바 설정
        setSupportActionBar(binding.toolbarIdSearch)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "칼로리 계산기"
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarIdSearch.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}




