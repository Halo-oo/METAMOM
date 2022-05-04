package com.metamom.bbssample.FoodListMeals


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
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.metamom.bbssample.MemberDao
import com.metamom.bbssample.R
import com.metamom.bbssample.subsingleton.MemberSingleton
import kotlinx.android.synthetic.main.activity_add_food_list.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddFoodList : AppCompatActivity() {
    var imgUri:String = ""

    //카메라 , 스토리지 권한 변수
    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE)
    // 카메라 스토리지 정해진 상수값.
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    
    @RequiresApi(Build.VERSION_CODES.O) // 날짜 어노테이션
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food_list)

        val cameraBtn = findViewById<ImageButton>(R.id.cameraBtn)
        val albumBtn = findViewById<ImageButton>(R.id.albumBtn)
        val scoreText = findViewById<TextView>(R.id.scoreText) // 맛점수
        val editMemoText = findViewById<EditText>(R.id.editMemoText)// 메모
        var date = LocalDateTime.now()

        if(imgUri == null){
            imgUri = null.toString()
        }
        //카메라
        cameraBtn.setOnClickListener {
            camera()
            println("!!!!!!!!!! : " + imgUri)

        }
        //앨범
        albumBtn.setOnClickListener {
            Album()
            println("!!!!!!!!!! : " + imgUri)
        }
        //음식 점수 radio group
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener{ group,checkedId ->
            when(checkedId){
                R.id.point1 -> scoreText.text = "1점"
                R.id.point2 -> scoreText.text = "2점"
                R.id.point3 -> scoreText.text = "3점"
                R.id.point4 -> scoreText.text = "4점"
                R.id.point5 -> scoreText.text = "5점"
            }
        }
        //분류 스피너
        spinnerTimeKind() // 스피너
        spinnerSelect() // 선택

        //작성
        val saveListBtn = findViewById<Button>(R.id.saveListBtn)
        saveListBtn.setOnClickListener {
            FoodListMealsDao.getInstance().FoodListTest(FoodListMealsDto
                                                        ("${MemberSingleton.id}",
                                                        0,
                                                        "${date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",//YYYY-MM-DD 형식으로 저장
                                                        "${selectKind.text}",
                                                        "${editMemoText.text}",
                                                        "${imgUri}",
                                                        "${scoreText.text}",
                                                        0))
            Toast.makeText(this,"저장완료",Toast.LENGTH_SHORT).show()

            val i = Intent(this, FoodListMeals::class.java)
            startActivity(i)
            //확인용
            /*val List = FoodListMealsDao.getInstance().FoodListSelect()
            println(List[0].toString())
            Toast.makeText(this,"${List[0].toString()}",Toast.LENGTH_LONG).show()
*/
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

    fun getPath(uri:Uri?):String{
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor:Cursor =managedQuery(uri,projection,null,null,null)
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
    fun fileSava(fileName:String,mimeType:String , bitmap: Bitmap):Uri?{
        var CV= ContentValues()

        //mediaStore에 파일명 타입 지정
        CV.put(MediaStore.Images.Media.DISPLAY_NAME,fileName)
        CV.put(MediaStore.Images.Media.MIME_TYPE,mimeType)

        //안전성 검사
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.Q){
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

        val albumImg = findViewById<ImageView>(R.id.albumImg)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                CAMERA_CODE ->{
                    if(data?.extras?.get("data")!=null){
                        val img = data?.extras?.get("data") as Bitmap
                        val uri = fileSava(RandomFileName(), "image/jpeg", img)
                        albumImg.setImageURI(uri)
                        Toast.makeText(this,"$uri",Toast.LENGTH_SHORT).show()
                        println("경로: $uri")
                        println("실제 이미지 경로 : " +getPath(uri))
                        imgUri =  getPath(uri)
                    }
                }
                STORAGE_CODE ->{
                    val uri = data?.data
                    albumImg.setImageURI(uri)
                    imgUri =  getPath(uri)
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

    fun spinnerTimeKind(){
        val timeKind = resources.getStringArray(R.array.kind)

        val adapter = ArrayAdapter(this, R.layout.food_list_spinner,timeKind)

        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter=adapter
    }
    fun spinnerSelect(){
        val spinner = findViewById<Spinner>(R.id.spinner)
        val selectKind = findViewById<TextView>(R.id.selectKind)
        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                /*if(position == 0) {

                }*/
                selectKind.text = "${spinner.getItemAtPosition(position)}"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
}