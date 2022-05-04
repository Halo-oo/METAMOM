package com.metamom.bbssample

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(val context: Context, val dataList: ArrayList<MemberDto>) : RecyclerView.Adapter<MemberAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userId = itemView.findViewById<EditText>(R.id.idInput)
        val userPwd = itemView.findViewById<EditText>(R.id.pwdInput)
        val userEmail = itemView.findViewById<EditText>(R.id.emailInput)
        val userPhoneNumber = itemView.findViewById<EditText>(R.id.phoneNumberInput)
        val userHeight = itemView.findViewById<EditText>(R.id.heightInput)
        val userWeight = itemView.findViewById<EditText>(R.id.weightInput)
        val userBirth = itemView.findViewById<EditText>(R.id.birthInput)
        val userGender = itemView.findViewById<RadioGroup>(R.id.genderRadio)

        fun bind(dataVo: MemberDto, context: Context){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}