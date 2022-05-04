package com.metamom.bbssample.sns

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
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.metamom.bbssample.R
import com.metamom.bbssample.subsingleton.SnsSingleton
import kotlinx.android.synthetic.main.activity_sns_update.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat

class SnsUpdateActivity : AppCompatActivity() {

        //카메라 , 스토리지 권한 변수
        val CAMERA = arrayOf(Manifest.permission.CAMERA)
        val STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        // 카메라 스토리지 정해진 상수값.
        val CAMERA_CODE = 98
        val STORAGE_CODE = 99
        var newImgUri:String = ""





        //val snsUpdateContent2 = findViewById<EditText>(R.id.snsUpdateContentEditText)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sns_update)

            var snsUpdateImage = findViewById<ImageView>(R.id.snsUpdateImage)
            var snsUpdateContentEditText = findViewById<EditText>(R.id.snsUpdateContentEditText)
            val cameraBtn = findViewById<ImageButton>(R.id.snsCameraImageButton)
            val albumBtn = findViewById<ImageButton>(R.id.snsAlbumImageButton)




            //툴바 사용 설정
            setSupportActionBar(snsUpdateToolbar)
            // 툴바 왼쪽 버튼 설정
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar!!.title="수정"

            newImgUri = intent.getStringExtra("ImageContentUri")!!
            val uri = Uri.parse(newImgUri)
            snsUpdateImage.setImageURI(uri)

            //카메라 눌러서 변경했을 경우 imgSet 에도 링크 변경
            cameraBtn.setOnClickListener {
                camera()

            }
            //앨범 눌러서 변경했을 경우 imgSet 에도 링크 변경
            albumBtn.setOnClickListener{
                Album()

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

                            snsUpdateImage.setImageURI(uri)
                            println("경로: $uri")
                            println("실제 이미지 경로 : " +getPath(uri))
                            //newImgUri =  getPath(uri)
                            newImgUri = uri.toString()
                        }
                    }
                    STORAGE_CODE ->{
                        val uri = data?.data as Uri
                        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~$uri~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                        snsUpdateImage.setImageURI(uri)
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

    //툴바 메뉴 버튼 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sns_update_menu,menu)
        return true
    }
    //툴바 메뉴 버튼이 클릭 됐을 때 콜백
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            //왼쪽 버튼이 클릭 됐을때 (뒤로가기 버튼)
            android.R.id.home ->{
                finish()
                return true
            }
            R.id.snsUpdateInsertBtn ->{
                val dto = SnsDto(intent.getSerializableExtra("seq") as Int,"","","","",newImgUri,0,0,snsUpdateContentEditText.text.toString())
                SnsDao.getInstance().snsUpdate(dto)
                SnsSingleton.imageUri = newImgUri
                SnsSingleton.content = snsUpdateContentEditText.text.toString()

                val data = intent.getSerializableExtra("posi") as Int
                val i = Intent()
                i.putExtra("position",data)
                i.putExtra("uri",newImgUri)
                i.putExtra("content",snsUpdateContentEditText.text.toString())
                setResult(Activity.RESULT_OK,i)
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}