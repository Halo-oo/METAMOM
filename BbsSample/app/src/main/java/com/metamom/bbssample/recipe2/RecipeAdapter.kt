package com.metamom.bbssample.recipe2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.metamom.bbssample.R

class RecipeAdapter(private val context: Context, private val dataList: ArrayList<RecipeRecyclerDto>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

// 이 클래스를 Dto로 봐도 됨
class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val reciImage = itemView.findViewById<ImageView>(R.id.reciItemImage)
    private val reciContent = itemView.findViewById<TextView>(R.id.reciItemContent)

    fun bind(dto: RecipeRecyclerDto, context: Context){
        reciImage.setImageBitmap(ImageRoader().getBitmapImg( dto!!.recipeImage))
        /*
        if(dto.recipeImage != ""){
            //val resourceId = context.resources.getIdentifier(dto.recipeImage, "drawable", context.packageName)
            val resourceId = context.resources.getIdentifier(dto.recipeImage,"",context.packageName)

            if(resourceId > 0){
                reciImage.setImageResource(resourceId)
            }

            else{
                Glide.with(itemView).load(dto.recipeImage).into(reciImage)
            }

        }else{
            reciImage.setImageResource(R.drawable.ic_launcher_foreground)
        }
        */

        // 리사클러뷰에 레시피 뿌리기
        reciContent.text = dto.recipe

    }
}
