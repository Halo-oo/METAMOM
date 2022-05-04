package com.metamom.bbssample

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import com.metamom.bbssample.databinding.ActivitySearchIdBinding
import kotlinx.android.synthetic.main.activity_search_id.*
import kotlinx.android.synthetic.main.dialog_id_search.*

class SearchId : AppCompatActivity() {
    private val binding by lazy { ActivitySearchIdBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val editName = findViewById<EditText>(R.id.editName)
        val editBirth = findViewById<EditText>(R.id.editBirth)
        val searchIdBtn = findViewById<Button>(R.id.searchIdBtn)
        val resultText = findViewById<TextView>(R.id.resultText)

        searchIdBtn.setOnClickListener {
                val id = MemberDao.getInstance().searchId(MemberDto(
                    "", "", editName.text.toString(), "",
                    "", "", "", 0.0,
                    0.0, "n", 0, 0, editBirth.text.toString(),
                    0.0, ""
                ))
            println("~~~~~~~~~~~~~~~~~~~~~~~id:$id")
            if(id == null || id == "") {
                customDialogFunctionError()
            }else {
                resultText.isVisible = true
                resultText.setTextAppearance(this, R.style.normalText)
                resultText.text = "${editName.text}님의 아이디는 " + id + "입니다."
            }
        }

        // 액션바 설정
        setSupportActionBar(binding.toolbarIdSearch)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "아이디 찾기"
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarIdSearch.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun customDialogFunction(){
        val customDialog = Dialog(this)
        val resultId = findViewById<TextView>(R.id.resultId)

        val id = MemberDao.getInstance().searchId(MemberDto(
            "", "", editName.text.toString(), "",
            "", "", "", 0.0,
            0.0, "n", 0, 0, editBirth.text.toString(),
            0.0, ""
        ))

        resultId.setText(id)

        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(R.layout.dialog_id_search)
        customDialog.setCancelable(false)

        customDialog.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
            customDialog.dismiss()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        customDialog.show()
    }

    private fun customDialogFunctionError(){
        val customDialog = Dialog(this)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(R.layout.dialog_id_search_error)
        customDialog.setCancelable(false)
        customDialog.findViewById<TextView>(R.id.tv_error_submit).setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
}