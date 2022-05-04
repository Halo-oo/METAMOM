package com.metamom.bbssample.FoodListMeals

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.metamom.bbssample.R

class FoodListDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list_detail)

        val data = intent.getParcelableExtra<FoodListMealsDto>("allData")
        val imageView = findViewById<ImageView>(R.id.imageView)
        val kindTxt = findViewById<TextView>(R.id.kindTxt)
        val dateTxt = findViewById<TextView>(R.id.dateTxt)
        val scoreTxt = findViewById<TextView>(R.id.scoreTxt)
        val memoTxt2 = findViewById<TextView>(R.id.memoTxt2)
        var seqfoodlist:Int = data!!.seqfoodlist

        kindTxt.text = data?.meals

        /* #21# (04.14) 동영상 녹화를 위해 날짜만 Detail에 표시되도록 변경 */
        // [기존 code] dateTxt.text = data?.wdate
        dateTxt.text = (data?.wdate)?.substring(0 until 11)

        if (data?.foodscore =="1점"){ //점수
            scoreTxt.text="점수 : ★"
        }else if(data?.foodscore =="2점"){
            scoreTxt.text="점수 : ★★"
        }else if(data?.foodscore =="3점"){
            scoreTxt.text="점수 : ★★★"
        }else if(data?.foodscore =="4점"){
            scoreTxt.text="점수 : ★★★★"
        }else if(data?.foodscore =="5점"){
            scoreTxt.text="점수 : ★★★★★"
        }else scoreTxt.text="점수 : ${data?.foodscore}"

        if (kindTxt.text == "아침") {
            kindTxt.setTextColor(Color.parseColor("#8AC926"))
        } else if (kindTxt.text == "점심") {
            kindTxt.setTextColor(Color.parseColor("#FFB703"))
        } else if (kindTxt.text == "저녁") {
            kindTxt.setTextColor(Color.parseColor("#6A00F4"))
        }

        memoTxt2.text = data?.memo

        try {
            if(data?.imgUrl != null) {
                val uri = Uri.parse("${data.imgUrl}")
                imageView.setImageURI(uri)
            }else{
                imageView.setImageResource(R.drawable.xbox)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val delBtn = findViewById<Button>(R.id.delBtn)

        //삭제 delete
        delBtn.setOnClickListener {
            // 다이얼로그를 생성하기 위해 Builder 클래스 생성자를 이용
            val builder = AlertDialog.Builder(this)
            builder.setTitle("삭제.")
                .setMessage("정말 삭제 하시겟습니까 ?.")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        FoodListMealsDao.getInstance().deleteFoodList(seqfoodlist)
                        Toast.makeText(this,"삭제되었습니다.",Toast.LENGTH_LONG).show()
                        val i = Intent(this, FoodListMeals::class.java)
                        startActivity(i)
                    })
                .setNegativeButton("취소",null)
                .create()
            // 다이얼로그를 띄워주기
            builder.show()
        }
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        //수정 update

        updateBtn.setOnClickListener {
            println("!!!!!!!!!" + seqfoodlist)
            val i = Intent(this, FoodListMealsUpdate::class.java)
                i.putExtra("seq",seqfoodlist)
                i.putExtra("meals",data.meals)
                i.putExtra("uri",data.imgUrl)
                i.putExtra("memo",data.memo)
                i.putExtra("foodscore",data.foodscore)
                //i.putExtra("wdate",data.wdate)
                //println("url뭐임 : " + data.imgUrl)
            startActivity(i)
        }


    }

}