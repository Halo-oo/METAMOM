package com.metamom.bbssample.sns

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.metamom.bbssample.MainButtonActivity
import com.metamom.bbssample.R
import com.metamom.bbssample.fragments.TalkFragment
import com.metamom.bbssample.subsingleton.MemberSingleton
import com.metamom.bbssample.subsingleton.SnsSingleton

class FragSnsCustomAdapter(val context: Context, val snsList:ArrayList<SnsDto>, fragmentmanager : FragmentManager)  : RecyclerView.Adapter<FragSnsCustomAdapter.ItemViewHolder>(){
    private var mFragmentManager : FragmentManager
    init{
        mFragmentManager = fragmentmanager
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val contxt = context
        val snsProfile = itemView.findViewById<ImageView>(R.id.profileImageView)
        val snsNickName = itemView.findViewById<TextView>(R.id.nickNameTextView)
        val snsDate = itemView.findViewById<TextView>(R.id.dateTextView)
        val snsImageContent = itemView.findViewById<ImageView>(R.id.contentImageView)
        val snsLikeCount = itemView.findViewById<TextView>(R.id.likeCountTextView)
        val snsCommentCount = itemView.findViewById<TextView>(R.id.commentCountTextView)
        val snsContent = itemView.findViewById<TextView>(R.id.contentTextView)
        val likeBtn = itemView.findViewById<ImageButton>(R.id.likeImageButton)
        val snsCommentBtn = itemView.findViewById<ImageButton>(R.id.commentImageButton)
        val snsSettingBtn = itemView.findViewById<ImageButton>(R.id.settingImageButton)

        fun bind(dataVo: SnsDto, context: Context, mFragmentManager: FragmentManager){

            //프로필 이미지 뿌려주기
            if(dataVo.profile != ""){
                if(dataVo.profile.equals("profile3")){
                    val resourceId = context.resources.getIdentifier(dataVo.profile, "drawable", context.packageName)
                    println("~~~~~~~~~resourceId : ${resourceId}")
                    if(resourceId > 0){
                        snsProfile.setImageResource(resourceId)
                    }else{
                        Glide.with(itemView).load(dataVo.profile).into(snsProfile)
                    }
                }
                else{
                    val profileUri:Uri = Uri.parse(dataVo.profile)
                    snsProfile.setImageURI(profileUri)
                }
            } else{
                Glide.with(itemView).load(dataVo.profile).into(snsProfile)

            }

            //게시물 사진 올리기
            if(dataVo.imagecontent != ""){

                val snsUri: Uri = Uri.parse(dataVo.imagecontent)

                snsImageContent.setImageURI(snsUri)

            } else{
                snsImageContent.setImageResource(R.mipmap.ic_launcher_round) // 이미지 없다. 아무 이미지나 뿌린다
            }

            //게시물 올린 시간에 따라 다르게 뿌려줌
            val wdate = dataVo.snsdate!!.split("-")
            if(wdate.get(0).equals("0")){
                if(wdate.get(1).equals("0")) {
                    if(wdate.get(2).equals("0")){
                        snsDate.text="방금 전"
                    }else {
                        snsDate.text = "${wdate.get(2)}분 전"
                    }
                }else{
                    snsDate.text = "${wdate.get(1)}시간 전"
                }
            }else if(wdate.get(0).equals("1")){
                snsDate.text = "어제"
            }else{
                snsDate.text = "방금 전"
            }

            snsNickName.text = dataVo.nickname
            snsLikeCount.text = "좋아요 ${SnsDao.getInstance().snsLikeCount(dataVo.seq)}개"
            snsCommentCount.text = "댓글 ${SnsDao.getInstance().snsCommentCount(dataVo.seq)}개"
            snsContent.text = dataVo.content

            //좋아요 버튼 이미지 뿌려줄때
            var snsLikeCheck = SnsDao.getInstance().snsLikeCheck(SnsLikeDto(dataVo.seq, MemberSingleton.id!!,"YY/MM/DD"))
            println("~~~~~~~~~~~~~~~~~~~~~~$snsLikeCheck~~~~~~~~~~~~~~~~~~~~")
            if(snsLikeCheck > 0){
                val resourceId = context.resources.getIdentifier("ic_favorite_purple", "drawable", context.packageName)
                likeBtn.setImageResource(resourceId)
            }else{
                val resourceId = context.resources.getIdentifier("ic_favorite", "drawable", context.packageName)
                likeBtn.setImageResource(resourceId)
            }
            //뿌리고 난 후 좋아요 버튼을 눌렀을때
            likeBtn.setOnClickListener {
                val dto = SnsLikeDto(dataVo.seq, MemberSingleton.id!!,"YY/MM/DD")
                snsLikeCheck = SnsDao.getInstance().snsLikeCheck(dto)
                //좋아요가 눌려 있을때
                if(snsLikeCheck > 0){
                    //이미지 변경
                    val resourceId = context.resources.getIdentifier("ic_favorite", "drawable", context.packageName)
                    likeBtn.setImageResource(resourceId)
                    SnsDao.getInstance().snsLikeDelete(dto)

                }
                //좋아요가 안눌려 있을때
                else{
                    val resourceId = context.resources.getIdentifier("ic_favorite_purple", "drawable", context.packageName)
                    likeBtn.setImageResource(resourceId)

                    SnsDao.getInstance().snsLikeInsert(dto)

                }

                snsLikeCount.text = "좋아요 ${SnsDao.getInstance().snsLikeCount(dataVo.seq)}개"

            }
            //댓글 아이콘 클릭시
            snsCommentBtn.setOnClickListener {
                val n  = adapterPosition
                SnsSingleton.position = n
                SnsSingleton.code = "CMT"
                val i = Intent(context,CommentActivity::class.java)
                i.putExtra("pos",adapterPosition)
                i.putExtra("seq",dataVo.seq)
                contxt.startActivity(i)
            }
            //댓글 개수 클릭시
            snsCommentCount.setOnClickListener {
                val n  = adapterPosition
                SnsSingleton.position = n
                SnsSingleton.code = "CMT"
                val i = Intent(context,CommentActivity::class.java)
                i.putExtra("pos",adapterPosition)
                i.putExtra("seq",dataVo.seq)
                contxt.startActivity(i)

            }

            //셋팅 버튼 클릭시
            snsSettingBtn.setOnClickListener {
                //게시물 작성자와 현재 로그인한 유저가 같을 경우
                if(dataVo.id == MemberSingleton.id){
                    val BottomSheet = FragSnsBottomSheet(adapterPosition,this@FragSnsCustomAdapter,dataVo.seq,contxt,dataVo.imagecontent) as FragSnsBottomSheet
                    BottomSheet.show(mFragmentManager,BottomSheet.tag)
                }
                //다를경우
                else{
                    val BottomSheet = NotWriterBottomSheet(context)
                    BottomSheet.show(mFragmentManager,BottomSheet.tag)
                }

            }


        }


    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragSnsCustomAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sns_view_item_layout, parent, false)
        return ItemViewHolder(view)
    }



    override fun getItemCount(): Int {
        return snsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(snsList[position], context, mFragmentManager)
    }

    fun update(position: Int){
        notifyItemChanged(position)
    }
    fun delete(position:Int,seq:Int){
        SnsDao.getInstance().snsCommentAllDelete(seq)
        SnsDao.getInstance().snsLikeAllDelete(seq)
        SnsDao.getInstance().snsDelete(seq)
        notifyItemRemoved(position)
    }
    fun addFragSns(dto:SnsDto){
        snsList.add(dto)
        notifyItemInserted(0) //갱신

    }
}