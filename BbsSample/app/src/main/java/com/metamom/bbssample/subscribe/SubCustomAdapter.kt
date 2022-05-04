package com.metamom.bbssample.subscribe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.metamom.bbssample.R

/* #21# 구독 _오늘의 식단 RecyclerView에 드로잉하기 위하여 파일 사용 */
class SubCustomAdapter (val context: Context, val dataDto: Any) :RecyclerView.Adapter<SubItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sub_view_item_meal, parent, false)
        return SubItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubItemViewHolder, position: Int) {

        holder.bind(dataDto, context)
    }

    override fun getItemCount(): Int {
        //return dataDto.size
        return 1
    }
}


class SubItemViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    private val foodImage = itemView.findViewById<ImageView>(R.id.subItem_imageView)
    private val foodName = itemView.findViewById<TextView>(R.id.subItem_foodNameTxt)
    private val foodKcal = itemView.findViewById<TextView>(R.id.subItem_kcalTxt)
    private val foodAmount = itemView.findViewById<TextView>(R.id.subItem_amountTxt)      // #21# (04.05) 음식 양(g) 제외

    /* #21# sub_view_item_meal 레이아웃에 값 넣기(bind) */
    fun bind(subDataVo: Any, context: Context){

        // 1) [다이어트 식단] dataDto의 자료형이 SubDietMealDto(다이어트 식단)형이라면 다이어트 식단 Dto의 내용을 recycler에 binding
        if (subDataVo is SubDietMealDto){
            var imageUrl:String = subDataVo.subdfImage.toString()
            Glide.with(itemView).load(imageUrl).into(foodImage)

            foodName.text = subDataVo.subdfName
            //foodAmount.text = "${subDataVo.subdfAmount}g"
            foodAmount.text = "1인분"
            foodKcal.text = "${subDataVo.subdfKcal} Kcal"
        }

        // 2) [운동 식단] dataDto의 자료형이 SubExerMealDto(운동 식단)형이라면 운동 식단 Dto의 내용을 recycler에 binding
        if (subDataVo is SubExerMealDto){
            var imageUrl:String = subDataVo.subefImage.toString()
            Glide.with(itemView).load(imageUrl).into(foodImage)

            foodName.text = subDataVo.subefName
            //foodAmount.text = "${subDataVo.subefAmount} g"
            foodAmount.text = "1인분"
            foodKcal.text = "${subDataVo.subefKcal} Kcal"
        }

    }
}