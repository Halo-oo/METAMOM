package com.metamom.bbssample

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.metamom.bbssample.fragments.HomeFragment
import com.metamom.bbssample.sns.SnsCommentDto
import com.metamom.bbssample.sns.SnsDao
import com.metamom.bbssample.sns.SnsDto
import com.metamom.bbssample.subsingleton.MemberSingleton
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.activity_member_update.*
import kotlinx.android.synthetic.main.activity_sns_update.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat

/* #21# 회원정보 수정 */
class MemberUpdateActivity : AppCompatActivity() {

    //카메라 , 스토리지 권한 변수
    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    // 카메라 스토리지 정해진 상수값.
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    val userInfo = SnsDao.getInstance().snsGetMember(MemberSingleton.id.toString())
    var newImgUri:String = userInfo.profile!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_update)

        /* 액션바 설정 */
        setSupportActionBar(findViewById(R.id.memUpdateToolbar))
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        findViewById<Toolbar>(R.id.memUpdateToolbar).setNavigationOnClickListener {
            onBackPressed()
        }

        val profile = findViewById<CircleImageView>(R.id.accUpdateProfileImageView)     //META_SNS,SNS_COMMENT
        val cameraBtn = findViewById<ImageButton>(R.id.accUpdateCameraImageButton)
        val albumBtn = findViewById<ImageButton>(R.id.accUpdateAlbumImageButton)
        val id = findViewById<EditText>(R.id.memUpdate_idEditTxt)
        val nickname = findViewById<EditText>(R.id.memUpdate_nicknameEdit)              //META_SNS,SNS_COMMENT
        val name = findViewById<EditText>(R.id.memUpdate_nameEdit)
        val email = findViewById<EditText>(R.id.memUpdate_emailEdit)
        val phone = findViewById<EditText>(R.id.memUpdate_phoneEdit)
        val height = findViewById<EditText>(R.id.memUpdate_heightEdit)
        val weight = findViewById<EditText>(R.id.memUpdate_weightEdit)
        val birth = findViewById<EditText>(R.id.memUpdate_birthEdit)
        val genderGroup = findViewById<RadioGroup>(R.id.memUpdate_genderRadioGroup)
        val updateBtn = findViewById<Button>(R.id.memUpdate_updateBtn)



        //카메라 눌러서 변경했을 경우 imgSet 에도 링크 변경
        cameraBtn.setOnClickListener {
            camera()

        }
        //앨범 눌러서 변경했을 경우 imgSet 에도 링크 변경
        albumBtn.setOnClickListener{
            Album()

        }

        /* 현재 로그인한 사용자의 회원정보 출력 */

        Log.d("MemberUpdateActivity", "#21# 마이페이지 회원수정을 위하여 가져온 값 > $userInfo")

        if (userInfo != null){

            //프로필 이미지 뿌려주기
            if(userInfo.profile != ""){
                if(userInfo.profile.equals("profile3")){
                    val resourceId = this.resources.getIdentifier(userInfo.profile, "drawable", this.packageName)
                    if(resourceId > 0){
                        profile.setImageResource(resourceId)
                    }else{
                        println("에이이이이이이이~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                        Glide.with(this).load(userInfo.profile).into(profile)
                    }
                } else{
                    val profileUri:Uri = Uri.parse(userInfo.profile)
                    profile.setImageURI(profileUri)
                }
            }else{
                Glide.with(this).load(userInfo.profile).into(profile)
            }

            id.setText(userInfo.id)
            nickname.setText(userInfo.nickname)
            name.setText(userInfo.name)
            email.setText(userInfo.email)
            phone.setText(userInfo.phone)
            height.setText(userInfo.height.toString())
            weight.setText(userInfo.weight.toString())
            birth.setText(userInfo.birth)
        }
        else {
            Toast.makeText(this, "관리자에게 문의하여주시길 바립니다. 죄송합니다", Toast.LENGTH_LONG).show()
            Log.d("MemberUpdateActivity", "#21# MemberUpdateActivity 마이페이지 회원수정 페이지 > 회원정보 가져오기 실패 Error")
        }

        /* 회원정보 수정 */
        updateBtn.setOnClickListener {

            var isGoToUpdate = true

            // 1) 입력한 회원정보 각각의 변수에 저장
            val nickname = nickname.text.toString()
            val name = name.text.toString()
            val email = email.text.toString()
            val phone = phone.text.toString()
            val height = height.text.toString().toDouble()
            val weight = weight.text.toString().toDouble()
            val birth = birth.text.toString()

            // 남/여 Radio 버튼 값 불러오기
            var genderRadioBtn = findViewById<RadioButton>(genderGroup.checkedRadioButtonId)
            var gender :String? = genderRadioBtn.text.toString()

            if (gender == "남") gender = "M"
            else if (gender == "여") gender = "W"
            else gender = null

            // 2) 값이 비어있는지 확인 (유효성 검토)
            if (nickname.isNullOrEmpty()) {
                Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (name.isNullOrEmpty()) {
                Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (name.length > 20) {
                Toast.makeText(this, "이름을 20자 이내로 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (email.isNullOrEmpty()) {
                Toast.makeText(this, "이메일를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (phone.isNullOrEmpty()) {
                Toast.makeText(this, "전화번호를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (height.isNaN()) {
                Toast.makeText(this, "키를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (height.toString().length > 6) {
                Toast.makeText(this, "유효하지 않은 값입니다", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (weight.isNaN()) {
                Toast.makeText(this, "몸무게를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (weight.toString().length > 6) {
                Toast.makeText(this, "유효하지 않은 값입니다", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (birth.isNullOrEmpty()) {
                Toast.makeText(this, "생년월일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (birth.length > 9) {
                Toast.makeText(this, "유효하지 않은 값입니다", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }
            if (gender.isNullOrEmpty()) {
                Toast.makeText(this, "성별을 선택해주세요", Toast.LENGTH_LONG).show()
                isGoToUpdate = false
            }


            // 3) 회원정보 수정
            if (isGoToUpdate) {
                val userUpdate = MemberDao.getInstance().userUpdate(MemberDto(id.text.toString(), "", name, email, gender, phone, nickname, height, weight, "", 0, 0, birth, 0.0, newImgUri))
                Log.d("MemberUpdateActivity", "#21# MemberUpdateActivity _Back으로 부터 전달받은 회원정보 수정 결과값 > $userUpdate")
                val snsUpdate = SnsDao.getInstance().snsImgUpdate(SnsDto(0,MemberSingleton.id!!,nickname,newImgUri,"","",0,0,""))
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~${MemberSingleton.id}~~~~~~~~~~~$nickname~~~~~~~~~~~$newImgUri~~~~~~~~~~")
                val commentUpdate = SnsDao.getInstance().snsCommentUpdate(SnsCommentDto(10,10,MemberSingleton.id!!,nickname,newImgUri,"",""))
                println("~~~~~~~~~~~~~~~~~~~~~$commentUpdate~~~~~~~~~~~~~~~~~~~~~~~~")
                if (userUpdate == true && snsUpdate != null && commentUpdate != null) {
                    Toast.makeText(this, "회원정보가 수정되었습니다!", Toast.LENGTH_LONG).show()

                    val i = Intent(this, MainButtonActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                else {
                    Toast.makeText(this, "관리자에게 문의하여 주시길 바랍니다. 죄송합니다", Toast.LENGTH_LONG).show()
                    Log.d("MemberUpdateActivity", "#21# MemberUpdateActivity _회원수정 실패 Error")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>, grantResults: IntArray){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_CODE -> {
                for (grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "카메라 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
            STORAGE_CODE -> {
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "저장소 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    fun getPath(uri: Uri?):String{
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor: Cursor =managedQuery(uri,projection,null,null,null)
        startManagingCursor(cursor)
        val columnIndex : Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }
    //다른 권한등록 확인
    fun checkPermission(permissions: Array<out String>, type:Int):Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (permission in permissions){
                if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, permissions, type)
                    return false
                }
            }
        }
        return true
    }
    fun camera(){ //카메라 촬영
        if(checkPermission(CAMERA,CAMERA_CODE) && checkPermission(STORAGE,STORAGE_CODE)){
            val itent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(itent,CAMERA_CODE)
        }
    }
    fun fileSava(fileName:String,mimeType:String , bitmap: Bitmap): Uri?{
        var CV= ContentValues()

        //mediaStore에 파일명 타입 지정
        CV.put(MediaStore.Images.Media.DISPLAY_NAME,fileName)
        CV.put(MediaStore.Images.Media.MIME_TYPE,mimeType)
        //안전성 검사
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CV.put(MediaStore.Images.Media.IS_PENDING,1)
        }
        //store에 파일 저장
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,CV)
        if(uri != null){
            var scriptor = contentResolver.openFileDescriptor(uri,"w")
            val fos = FileOutputStream(scriptor?.fileDescriptor)

            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos)
            fos.close()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                CV.clear()

                //IS_PENDING을 초기화해준다 .
                CV.put(MediaStore.Images.Media.IS_PENDING,0)
                contentResolver.update(uri,CV,null,null)
            }
        }
        return uri
    }
    //결과
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                CAMERA_CODE ->{
                    if(data?.extras?.get("data")!=null){
                        val img = data?.extras?.get("data") as Bitmap
                        val uri = fileSava(RandomFileName(), "image/jpeg", img)

                        accUpdateProfileImageView.setImageURI(uri)
                        println("경로: $uri")
                        println("실제 이미지 경로 : " +getPath(uri))
                        //newImgUri =  getPath(uri)
                        newImgUri = uri.toString()
                    }
                }
                STORAGE_CODE ->{
                    val uri = data?.data as Uri
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~$uri~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                    accUpdateProfileImageView.setImageURI(uri)
                    //newImgUri =  getPath(uri)
                    newImgUri = uri.toString()
                }
            }
        }

    }
    //파일명 날짜로 저장
    fun RandomFileName() : String{
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        return fileName
    }
    //갤러리 가져오기
    fun Album(){
        if(checkPermission(STORAGE,STORAGE_CODE)){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent,STORAGE_CODE)
        }
    }

}