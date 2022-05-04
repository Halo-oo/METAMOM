package com.metamom.bbssample

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import com.metamom.bbssample.databinding.ActivitySearchPwdBinding

class SearchPwd : AppCompatActivity() {
    private val binding by lazy { ActivitySearchPwdBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val editId2 = findViewById<EditText>(R.id.editId2)
        val editName2 = findViewById<EditText>(R.id.editName2)
        val editBrith2 = findViewById<EditText>(R.id.editBirth2)
        val searchPwdBtn = findViewById<Button>(R.id.searchPwdBtn)
        val resultText2 = findViewById<TextView>(R.id.resultText2)

        searchPwdBtn.setOnClickListener {

            var pwd = MemberDao.getInstance().searchPwd(MemberDto(
                editId2.text.toString(), "", editName2.text.toString(), "",
                "", "", "", 0.0,
                0.0, "n", 0, 0, editBrith2.text.toString(),
                0.0, ""
            )).toString()
            if(pwd == null || pwd == ""){
                customDialogFunctionError()
            }else {
                resultText2.isVisible = true
                resultText2.setTextAppearance(this, R.style.normalText)
                resultText2.text = "${editName2.text} 님의 비밀번호는 " + pwd + "입니다."
            }
        }

        // 액션바 설정
        setSupportActionBar(binding.toolbarPwdSearch)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "비밀번호 찾기"
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button);
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbarPwdSearch.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun customDialogFunctionError(){
        val customDialog = Dialog(this)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(R.layout.dialog_pwd_search_error)
        customDialog.setCancelable(false)
        customDialog.findViewById<TextView>(R.id.tv_error_pwd_submit).setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

}