package com.metamom.bbssample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.metamom.bbssample.sns.SnsActivity
import com.metamom.bbssample.subscribe.SubscribeDao
import com.metamom.bbssample.subscribe.SubscribeDto
import com.metamom.bbssample.subsingleton.SubTodayMealSingleton
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.util.helper.Utility
import com.metamom.bbssample.subsingleton.MemberSingleton

class MainActivity : AppCompatActivity() {

    lateinit var id: EditText
    lateinit var pwd: EditText
    lateinit var loginBtn: Button
    lateinit var kakaoBtn: TextView
    lateinit var idSave: CheckBox
    lateinit var searchId : TextView
    lateinit var searchPwd : TextView

    // 구글 로그인 변수
    final val RC_SIGN_IN = 1
    lateinit var googleBtn: TextView
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.editId)
        pwd = findViewById(R.id.editPwd)
        loginBtn = findViewById(R.id.loginBtn)
        googleBtn = findViewById(R.id.googleBtn)
        kakaoBtn = findViewById(R.id.kakaoBtn)
        idSave = findViewById(R.id.idSave)
        searchId = findViewById(R.id.searchId)
        searchPwd = findViewById(R.id.searchPwd)

        loginBtn.setOnClickListener {

            val userId = id.text.toString()
            val userPwd = pwd.text.toString()

            var dto = MemberDao.getInstance()
                .login(
                    MemberDto(
                        userId, userPwd, "", "",
                        "", "", "", 0.0,
                        0.0, "n", 0, 0, "",
                        0.0, ""
                    )
                )

            if (dto != null) {

                MemberDao.user = dto
                MemberSingleton.id = dto.id
                Toast.makeText(this, "${dto.id}님 환영합니다", Toast.LENGTH_LONG).show()
                // login 되면 이동
                val i = Intent(this, MainButtonActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "ID나 PW를 확인하세요", Toast.LENGTH_LONG).show()
            }
        }
        //p 아이디 비밀번호 찾기
        searchId.setOnClickListener {
            val i = Intent(this,SearchId::class.java)
            startActivity(i)
        }
        searchPwd.setOnClickListener {
            val i = Intent(this,SearchPwd::class.java)
            startActivity(i)
        }


        /*loginBtn.setOnClickListener {

            val id = editId.text.toString()
            val password = editPw.text.toString()
            println("~~~~~~~~~~$id, $password")
            var dto = MemberDao.getInstance().login(MemberDto(id, password, "", "", 0))
            if(dto != null){
                MemberDao.user = dto

                Toast.makeText(this, "${dto.name}님 환영합니다", Toast.LENGTH_LONG).show()

                 //login 되면 이동
                //val i = Intent(this, BbsListActivity::class.java)

            }else { Toast.makeText(this, "ID나 PW를 확인하세요", Toast.LENGTH_LONG).show()
           }
        }*/

        val insertMemberBtn = findViewById<TextView>(R.id.insertMemberBtn)
        insertMemberBtn.setOnClickListener {

            val i = Intent(this, InsertActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            startActivity(i)
        }

        // 구글 로그인
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        googleBtn = findViewById(R.id.googleBtn)

        googleBtn.setOnClickListener {
            signIn()
            val i = Intent(this, MainButtonActivity::class.java)
            startActivity(i)
        }
    }

    private fun signIn() {
        var signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
            val givenName = account?.givenName.toString()
            val displayName = account?.displayName.toString()

            Log.d("account", email)
            Log.d("account", familyName)
            Log.d("account", givenName)
            Log.d("account", displayName)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }

        // 카카오 키해시 확인
        var keyHash = Utility.getKeyHash(this)
        println("keyhash : " + keyHash)

        // 카카오 로그인
        // 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e("MainActivity", "토큰 정보 보기 실패")
            }
            else if (tokenInfo != null) {
                Log.e("MainActivity", "토큰 정보 보기 성공")
                val intent = Intent(this, MainButtonActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                val intent = Intent(this, MainButtonActivity::class.java)
                startActivity(intent)
            }
        }

        kakaoBtn.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}


