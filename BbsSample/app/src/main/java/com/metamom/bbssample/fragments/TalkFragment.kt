package com.metamom.bbssample.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metamom.bbssample.R
import com.metamom.bbssample.databinding.FragmentTalkBinding
import com.metamom.bbssample.sns.FragSnsCustomAdapter
import com.metamom.bbssample.sns.SnsDao
import com.metamom.bbssample.sns.SnsInsertActivity
import com.metamom.bbssample.subsingleton.SnsSingleton
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.fragment_talk.*

class TalkFragment : Fragment() {



    private lateinit var binding : FragmentTalkBinding

    var data = SnsDao.getInstance().allSns()
    lateinit var adaptered:FragSnsCustomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val i = Intent(getActivity(),SnsActivity::class.java)
        startActivity(i)*/
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.snsFragmentRecyclerView)

        adaptered = FragSnsCustomAdapter(this.context!!,data!!,this.parentFragmentManager)
        recycler.adapter = adaptered
        val layout = LinearLayoutManager(this.context)
        recycler.layoutManager = layout
        recycler.setHasFixedSize(true)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(SnsSingleton.position != null){
            adaptered.notifyItemChanged(SnsSingleton.position!!)
        }

        //val recycler = container!!.findViewById<RecyclerView>(R.id.snsFragmentRecyclerView)


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        val recycler = binding.snsFragmentRecyclerView

        adaptered = FragSnsCustomAdapter(this.context!!,data!!,this.parentFragmentManager)
        recycler.adapter = adaptered
        val layout = LinearLayoutManager(this.context)
        recycler.layoutManager = layout
        recycler.setHasFixedSize(true)

        /*binding.snsFragmentRecyclerView.adapter = adaptered
        val layout = LinearLayoutManager(contxt)
        binding.snsFragmentRecyclerView.layoutManager = layout
        binding.snsFragmentRecyclerView.setHasFixedSize(true)*/
        val insertBtn = binding.root.findViewById<ImageButton>(R.id.fragSnsInsertButton)

        insertBtn.setOnClickListener {
            SnsSingleton.insertCheck = 1
            val i = Intent(this.context,SnsInsertActivity::class.java)
            startActivity(i)

        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.mealTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_mealFragment)
        }

        binding.recipeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_recipeFragment)
        }

        binding.accountTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_accountFragment)
        }
        return binding.root
    }




    override fun onResume() {
        super.onResume()
        println("~~~~~~~~~~~~~~~~~~~~~${SnsSingleton.position}~~~~~~~~~~~~~~${SnsSingleton.code}~~~~~~~~~~~~~~~성공!!!!!!")
        if(!SnsSingleton.code.equals("")){

            if(SnsSingleton.code.equals("CMT")) {
                adaptered.notifyItemChanged(SnsSingleton.position!!)
                adaptered.update(SnsSingleton.position!!)
                SnsSingleton.position = null
                SnsSingleton.code = ""
            }

            if(SnsSingleton.code.equals("SNSUPDATE")) {

                adaptered.snsList.get(SnsSingleton.position!!).imagecontent = SnsSingleton.imageUri
                adaptered.snsList.get(SnsSingleton.position!!).content = SnsSingleton.content

                adaptered.notifyItemChanged(SnsSingleton.position!!)
                adaptered.update(SnsSingleton.position!!)
                SnsSingleton.position = null
                SnsSingleton.imageUri = ""
                SnsSingleton.content = ""
                SnsSingleton.code = ""
            }

            if(SnsSingleton.code.equals("SNSINSERT")) {
                //index를 정해줘야 notifyItemInserted할때 0번으로 찾을 수 있음 안정하면 맨 마지막에 추가됨 그래서 0번지가 두개 복사됨
                adaptered.snsList.add(0,SnsSingleton.snsDto!!)
                adaptered.notifyItemInserted(0)
                snsFragmentRecyclerView.scrollToPosition(0)

                SnsSingleton.snsDto = null
                SnsSingleton.code = ""
            }
        }


    }


}