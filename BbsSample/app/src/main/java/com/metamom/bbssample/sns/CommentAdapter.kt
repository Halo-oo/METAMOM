package com.metamom.bbssample.sns

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.metamom.bbssample.R
import com.metamom.bbssample.subsingleton.MemberSingleton


class CommentAdapter(val context: Context, val commentList:ArrayList<SnsCommentDto>) : RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cmtProfile = itemView.findViewById<ImageView>(R.id.cmtProfileimageView)
        val cmtNickName = itemView.findViewById<TextView>(R.id.cmtNickNameTxt)
        val cmtWriteTime = itemView.findViewById<TextView>(R.id.cmtWriteTimeTxt)
        val cmtContent = itemView.findViewById<TextView>(R.id.cmtContentTxt)
        val cmtDeleteBtn = itemView.findViewById<ImageButton>(R.id.commentDelBtn)


        fun bind(dataVo: SnsCommentDto, context: Context) {
            cmtNickName.text = dataVo.nickname
            cmtContent.text = dataVo.content

            //댓글을 달은 시간에 따라 다르게 뿌려줌
            val wdate = dataVo.wdate!!.split("-")
            if(wdate.get(0).equals("0")){
                if(wdate.get(1).equals("0")) {
                    if(wdate.get(2).equals("0")) {
                        cmtWriteTime.text = "방금"
                    }else{
                        cmtWriteTime.text = "${wdate.get(2)}분"
                    }
                }else{
                    cmtWriteTime.text = "${wdate.get(1)}시간"
                }
            }else if(wdate.get(0).equals("1")){
                cmtWriteTime.text = "어제"
            }else if(wdate.get(0).equals("방금")){
                cmtWriteTime.text = "방금"
            }
            else{
                cmtWriteTime.text = "${wdate.get(0)}일"
            }

            //프로필 이미지 뿌려주기
            if(dataVo.profile != ""){
                if(dataVo.profile.equals("profile3")){
                    val resourceId = context.resources.getIdentifier(dataVo.profile, "drawable", context.packageName)
                    if(resourceId > 0){
                        cmtProfile.setImageResource(resourceId)
                    }else{
                        Glide.with(itemView).load(dataVo.profile).into(cmtProfile)
                    }
                }else{
                    val profileUri: Uri = Uri.parse(dataVo.profile)
                    cmtProfile.setImageURI(profileUri)
                }
            } else{
                Glide.with(itemView).load(dataVo.profile).into(cmtProfile)
            }
            //삭제 버튼 실행
            cmtDeleteBtn.setOnClickListener {
                if(dataVo.id == MemberSingleton.id){
                    val position = adapterPosition
                    SnsDao.getInstance().snsCommentDelete(dataVo.cmtseq!!)
                    commentList.removeAt(position)
                    deleteComment(position)
                    Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "작성자가 아닙니다.", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_view_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(commentList[position], context)



    }

    override fun getItemCount(): Int {
        return commentList.size
    }



    fun addComment(dto:SnsCommentDto,seq:Int){
        commentList.add(dto)
        notifyItemInserted(commentList.size-1) //갱신

    }

    fun deleteComment(position: Int){
        notifyItemRemoved(position)

    }


}