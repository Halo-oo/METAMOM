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

            //????????? ????????? ????????????
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

            //????????? ?????? ?????????
            if(dataVo.imagecontent != ""){

                val snsUri: Uri = Uri.parse(dataVo.imagecontent)

                snsImageContent.setImageURI(snsUri)

            } else{
                snsImageContent.setImageResource(R.mipmap.ic_launcher_round) // ????????? ??????. ?????? ???????????? ?????????
            }

            //????????? ?????? ????????? ?????? ????????? ?????????
            val wdate = dataVo.snsdate!!.split("-")
            if(wdate.get(0).equals("0")){
                if(wdate.get(1).equals("0")) {
                    if(wdate.get(2).equals("0")){
                        snsDate.text="?????? ???"
                    }else {
                        snsDate.text = "${wdate.get(2)}??? ???"
                    }
                }else{
                    snsDate.text = "${wdate.get(1)}?????? ???"
                }
            }else if(wdate.get(0).equals("1")){
                snsDate.text = "??????"
            }else{
                snsDate.text = "?????? ???"
            }

            snsNickName.text = dataVo.nickname
            snsLikeCount.text = "????????? ${SnsDao.getInstance().snsLikeCount(dataVo.seq)}???"
            snsCommentCount.text = "?????? ${SnsDao.getInstance().snsCommentCount(dataVo.seq)}???"
            snsContent.text = dataVo.content

            //????????? ?????? ????????? ????????????
            var snsLikeCheck = SnsDao.getInstance().snsLikeCheck(SnsLikeDto(dataVo.seq, MemberSingleton.id!!,"YY/MM/DD"))
            println("~~~~~~~~~~~~~~~~~~~~~~$snsLikeCheck~~~~~~~~~~~~~~~~~~~~")
            if(snsLikeCheck > 0){
                val resourceId = context.resources.getIdentifier("ic_favorite_purple", "drawable", context.packageName)
                likeBtn.setImageResource(resourceId)
            }else{
                val resourceId = context.resources.getIdentifier("ic_favorite", "drawable", context.packageName)
                likeBtn.setImageResource(resourceId)
            }
            //????????? ??? ??? ????????? ????????? ????????????
            likeBtn.setOnClickListener {
                val dto = SnsLikeDto(dataVo.seq, MemberSingleton.id!!,"YY/MM/DD")
                snsLikeCheck = SnsDao.getInstance().snsLikeCheck(dto)
                //???????????? ?????? ?????????
                if(snsLikeCheck > 0){
                    //????????? ??????
                    val resourceId = context.resources.getIdentifier("ic_favorite", "drawable", context.packageName)
                    likeBtn.setImageResource(resourceId)
                    SnsDao.getInstance().snsLikeDelete(dto)

                }
                //???????????? ????????? ?????????
                else{
                    val resourceId = context.resources.getIdentifier("ic_favorite_purple", "drawable", context.packageName)
                    likeBtn.setImageResource(resourceId)

                    SnsDao.getInstance().snsLikeInsert(dto)

                }

                snsLikeCount.text = "????????? ${SnsDao.getInstance().snsLikeCount(dataVo.seq)}???"

            }
            //?????? ????????? ?????????
            snsCommentBtn.setOnClickListener {
                val n  = adapterPosition
                SnsSingleton.position = n
                SnsSingleton.code = "CMT"
                val i = Intent(context,CommentActivity::class.java)
                i.putExtra("pos",adapterPosition)
                i.putExtra("seq",dataVo.seq)
                contxt.startActivity(i)
            }
            //?????? ?????? ?????????
            snsCommentCount.setOnClickListener {
                val n  = adapterPosition
                SnsSingleton.position = n
                SnsSingleton.code = "CMT"
                val i = Intent(context,CommentActivity::class.java)
                i.putExtra("pos",adapterPosition)
                i.putExtra("seq",dataVo.seq)
                contxt.startActivity(i)

            }

            //?????? ?????? ?????????
            snsSettingBtn.setOnClickListener {
                //????????? ???????????? ?????? ???????????? ????????? ?????? ??????
                if(dataVo.id == MemberSingleton.id){
                    val BottomSheet = FragSnsBottomSheet(adapterPosition,this@FragSnsCustomAdapter,dataVo.seq,contxt,dataVo.imagecontent) as FragSnsBottomSheet
                    BottomSheet.show(mFragmentManager,BottomSheet.tag)
                }
                //????????????
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
        notifyItemInserted(0) //??????

    }
}