package com.metamom.bbssample.FoodListMeals

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.metamom.bbssample.R


class FoodListAdapter(val context:Context,val writeFoodSelect:ArrayList<FoodListMealsDto>) : RecyclerView.Adapter<FoodListAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val foodImg = itemView.findViewById<ImageView>(R.id.foodImg)
        val foodKindTxt = itemView.findViewById<TextView>(R.id.foodKindTxt)
        //val timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
        val foodScoreTxt = itemView.findViewById<TextView>(R.id.foodScoreTxt)
        val memoTxt = itemView.findViewById<TextView>(R.id.memoTxt)
        var seqfoodlist:Int = 0
        var del:Int = 0
       fun bind(dataVo:FoodListMealsDto,context: Context){
           //시퀀스값
           seqfoodlist=dataVo.seqfoodlist
           //del값
           del=dataVo.del
           //foodImg  사진/카메라로 찍은 사진
           foodKindTxt.text = dataVo.meals //식사종류
           //timeTxt.text = dataVo.wdate //시간
           memoTxt.text = dataVo.memo // 메모
           if (dataVo.foodscore =="1점"){ //점수
               foodScoreTxt.text="점수 : ★"
           }else if(dataVo.foodscore =="2점"){
               foodScoreTxt.text="점수 : ★★"
           }else if(dataVo.foodscore =="3점"){
               foodScoreTxt.text="점수 : ★★★"
           }else if(dataVo.foodscore =="4점"){
               foodScoreTxt.text="점수 : ★★★★"
           }else if(dataVo.foodscore =="5점"){
               foodScoreTxt.text="점수 : ★★★★★"
               foodScoreTxt.setTextColor(Color.parseColor("#000000"))
               foodScoreTxt.setTextAppearance(context, R.style.emphasisText)
           }else foodScoreTxt.text="점수 : ${dataVo.foodscore}"
          // foodImg.setImageURI((Vo.imgUrl))

           if (foodKindTxt.text == "아침") {
               foodKindTxt.setTextColor(Color.parseColor("#8AC926"))
           } else if (foodKindTxt.text == "점심") {
               foodKindTxt.setTextColor(Color.parseColor("#FFB703"))
           } else if (foodKindTxt.text == "저녁") {
               foodKindTxt.setTextColor(Color.parseColor("#6A00F4"))
           }

           try {
               if(dataVo.imgUrl != null) {
                   val uri = Uri.parse("${dataVo.imgUrl}")
                   foodImg.setImageURI(uri)
               }else{
                   foodImg.setImageResource(R.drawable.xbox)
               }
           } catch (e: Exception) {
               e.printStackTrace()
           }

           //itemView 클릭시 디테일화면
           itemView.setOnClickListener {
               Intent(context, FoodListDetail::class.java).apply {
                   putExtra("allData",dataVo)
                   addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               }.run { context.startActivity(this) }
           }
       }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.meals_view_item_layout,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodListAdapter.ItemViewHolder, position: Int) {
        holder.bind(writeFoodSelect[position],context)
    }

    override fun getItemCount(): Int {
        return writeFoodSelect.size
    }
}


