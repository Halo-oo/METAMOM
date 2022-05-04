package com.metamom.bbssample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.metamom.bbssample.databinding.ActivityInsertBinding

class InsertActivity : AppCompatActivity() {

    lateinit var id: EditText
    lateinit var pwd: EditText
    lateinit var pwd2: EditText
    lateinit var name: EditText
    lateinit var nickName: EditText
    lateinit var email: EditText
    lateinit var phone: EditText
    lateinit var height: EditText
    lateinit var weight: EditText
    lateinit var birth: EditText
    lateinit var gender: RadioGroup
    lateinit var registerBtn: Button

    private val binding by lazy { ActivityInsertBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = findViewById(R.id.idInput)
        pwd = findViewById(R.id.pwdInput)
        pwd2 = findViewById(R.id.pwdInputCheck)
        name = findViewById(R.id.nameInput)
        nickName = findViewById(R.id.nicknameInput)
        email = findViewById(R.id.emailInput)
        phone = findViewById(R.id.phoneNumberInput)
        height = findViewById(R.id.heightInput)
        weight = findViewById(R.id.weightInput)
        birth = findViewById(R.id.birthInput)
        gender = findViewById(R.id.genderRadio)
        registerBtn = findViewById(R.id.registerBtn)

        registerBtn.setOnClickListener {

            var isGoToJoin = true

            /* #21# ID 중복체크 */
            // - [기존 code] val userId = id.text.toString()
            // - [변경 code] ID 중복체크 후 중복된 ID가 없을 경우 userID 변수에 사용자가 입력한 id 값을 넣음
            var userId :String? = null

            val idCheck = MemberDao.getInstance().idCheck(id.text.toString())       // ID 중복체크
            println("~~~~~~~~~~~~~~~~~~~~~~$idCheck~~~~~~~~~~~~~~~~~~~~~~~~~~")
            if (idCheck == false) {
                userId = id.text.toString()
            } else {
                Toast.makeText(this, "중복된 ID가 있습니다. 새로운 ID로 입력해주세요", Toast.LENGTH_LONG).show()
                id.setText("")
                isGoToJoin = false
            }

            val userPwd = pwd.text.toString()
            val userPwd2 = pwd2.text.toString()
            val userName = name.text.toString()
            val userNickName = nickName.text.toString()
            val userEmail = email.text.toString()
            val userPhone = phone.text.toString()
            val userHeight = height.text.toString().toDouble()
            val userWeight = weight.text.toString().toDouble()
            val userBirth = birth.text.toString()

            // 라디오 버튼인 gender의 값 불러오기
            var userGenderBtn = findViewById<RadioButton>(gender.checkedRadioButtonId)
            var userGender = userGenderBtn.text.toString()
            // #21# gender DB 저장 시 남은 == M으로 여는 == W로 저장
            if (userGender == "남"){
                userGender = "M"
            } else if (userGender == "여"){
                userGender = "W"
            }

            // 값이 비어있는지 확인
            if (isGoToJoin) {
                if (userId.isNullOrEmpty()) {
                    Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userId?.length!! > 12) {
                    Toast.makeText(this, "아이디를 12자 이내로 입력해주세요", Toast.LENGTH_LONG).show()
                    isGoToJoin = false
                }
                if (userPwd.isNullOrEmpty()) {
                    Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userPwd2.isNullOrEmpty()) {
                    Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userEmail.isNullOrEmpty()) {
                    Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userName.isNullOrEmpty()) {
                    Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userName.length > 20) {
                    Toast.makeText(this, "이름을 20자 이내로 입력해주세요", Toast.LENGTH_LONG).show()
                    isGoToJoin = false
                }
                if (userNickName.isNullOrEmpty()) {
                    Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userPhone.isNullOrEmpty()) {
                    Toast.makeText(this, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userHeight.isNaN()) {
                    Toast.makeText(this, "키를 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userHeight.toString().length > 6) {
                    Toast.makeText(this, "유효하지 않은 값입니다", Toast.LENGTH_LONG).show()
                    isGoToJoin = false
                }
                if (userWeight.isNaN()) {
                    Toast.makeText(this, "몸무게를 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userWeight.toString().length > 6) {
                    Toast.makeText(this, "유효하지 않은 값입니다", Toast.LENGTH_LONG).show()
                    isGoToJoin = false
                }
                if (userBirth.isNullOrEmpty()) {
                    Toast.makeText(this, "생년월일을 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                if (userBirth.length > 9) {
                    Toast.makeText(this, "유효하지 않은 값입니다", Toast.LENGTH_LONG).show()
                    isGoToJoin = false
                }
                // 비밀번호 2개가 같은지 확인
                if (userPwd != userPwd2) {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
                // 비밀번호가 6자 이상인지
                if (userPwd.length < 6) {
                    Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요", Toast.LENGTH_SHORT).show()
                    isGoToJoin = false
                }
            }

            if (isGoToJoin) {
                val dto=MemberDao.getInstance().addmember(
                    MemberDto(
                        userId, userPwd, userName,
                        userEmail, userGender, userPhone, userNickName,
                        userHeight, userWeight, "n", 0, 0,
                        userBirth, 0.0, ""
                    )

                )
                println(dto)

                Toast.makeText(this, "가입되었습니다", Toast.LENGTH_LONG).show()

                val i = Intent(this, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }

        // 액션바 설정
        setSupportActionBar(binding.toolbarInsertMember)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button);
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbarInsertMember.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
