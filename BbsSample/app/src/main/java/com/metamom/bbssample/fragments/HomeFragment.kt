package com.metamom.bbssample.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.metamom.bbssample.FoodListMeals.FoodListMeals
import com.metamom.bbssample.KcalBMI.BmiMain
import com.metamom.bbssample.KcalBMI.KcalMain
import com.metamom.bbssample.InfiniteAdapter
import com.metamom.bbssample.LogoutActivity


import com.metamom.bbssample.R
import com.metamom.bbssample.WebViewActivity
import com.metamom.bbssample.databinding.FragmentHomeBinding
import com.metamom.bbssample.recipe2.RecipeMainActivity
import com.metamom.bbssample.sns.SnsActivity
import com.metamom.bbssample.subscribe.SubAddActivity
import com.metamom.bbssample.subscribe.SubInfoActivity
import com.metamom.bbssample.subsingleton.MemberSingleton

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private var list = mutableListOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private var numBanner = 3
    private var currentPosition = Int.MAX_VALUE / 2
    private var myHandler = MyHandler()
    private val intervalTime = 2000.toLong() // 몇초 간격으로 페이지를 넘길것인지 (1500 = 1.5초)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        /* #21# '구독 신청' Button 클릭 시 구독 여부 확인
         *   case_1) 구독일 경우 구독 정보 출력 페이지로 이동
         *   case_2) 구독이 아닐경우 구독 신청 페이지로 이동 */
        binding.mainSubBtn.setOnClickListener {

            if(MemberSingleton.subscribe == "0") {          // case_2) 구독이 아닐 경우
                val i = Intent(context, SubAddActivity::class.java)
                startActivity(i)
            }
            else {                                          // case_1) 구독일 경우
                Toast.makeText(context, "구독 회원입니다! 😉", Toast.LENGTH_LONG).show()

                val i = Intent(context,  SubInfoActivity::class.java)
                startActivity(i)
            }
        }

        //sns 이동 버튼
        binding.SnsBtn.setOnClickListener {
            val intent = Intent(context, SnsActivity::class.java)
            startActivity(intent)
        }

        binding.haebinBtn.setOnClickListener {
            val i = Intent(context, FoodListMeals::class.java)
            startActivity(i)
            Toast.makeText(context, "+ 버튼을 누르고 오늘의 식사를 기록해보세요\uD83C\uDF7D", Toast.LENGTH_LONG).show()
        }

        /* #21# 오늘의 식단 Button */
        binding.mainSubTodayMealBtn.setOnClickListener {
            if (MemberSingleton.subscribe == "0") {          // case_2) 구독이 아닐 경우
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("오늘의 식단")
                builder.setMessage("구독회원 전용 서비스 입니다 😥")
                builder.show()
            }
            else {                                          // case_1) 구독일 경우
                it.findNavController().navigate(R.id.action_homeFragment_to_mealFragment)
            }
        }

        binding.recipeBtn.setOnClickListener {
            val i = Intent(context, RecipeMainActivity::class.java)
            startActivity(i)
        }
        binding.KcalBtn.setOnClickListener {
            val intent = Intent(context,KcalMain::class.java)
            startActivity(intent)
        }
        binding.BmiBtn.setOnClickListener {
            val intent = Intent(context,BmiMain::class.java)
            startActivity(intent)
        }

        binding.btnMenu.setOnClickListener {
            val i = Intent(context, LogoutActivity::class.java)
            startActivity(i)
        }

        binding.textViewTotalBanner.text = numBanner.toString()
        binding.autoScrollViewPager.adapter = InfiniteAdapter(list)
        binding.autoScrollViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.autoScrollViewPager.setCurrentItem(currentPosition, false)

        binding.autoScrollViewPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.textViewCurrentBanner.text = "${(position % 3) + 1}"
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        // 뷰페이저 움직이는 중
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
            binding.autoScrollViewPager.setOnClickListener {
                val i = Intent(context, WebViewActivity::class.java)
                startActivity(i)
            }
        }

        binding.linearLayoutSeeAll.setOnClickListener {
            Toast.makeText(context, "모두 보기 클릭했음", Toast.LENGTH_SHORT).show()
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_self)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
            //val i = Intent(activity,SnsActivity::class.java)
            //startActivity(i)
        }

        /* #21# [구독] 오늘의 식단 navigation bar
        *  case_1) 구독일 경우 > MealFragment로 이동
        *  case_2) 구독이 아닐 경우 > HomeFragment로 이동 */
        binding.mealTap.setOnClickListener {

            if (MemberSingleton.subscribe == "0") {          // case_2) 구독이 아닐 경우
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("오늘의 식단")
                builder.setMessage("구독회원 전용 서비스 입니다 😥")
                builder.show()

                it.findNavController().navigate(R.id.action_homeFragment_self)
            }
            else {                                          // case_1) 구독일 경우
                it.findNavController().navigate(R.id.action_homeFragment_to_mealFragment)
            }
        }

        binding.recipeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_recipeFragment)
        }

        /* #21# 마이페이지 */
        binding.accountTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_accountFragment)
        }

        return binding.root
    }

    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0) // 이거 안하면 핸들러가 1개, 2개, 3개 ... n개 만큼 계속 늘어남
        myHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime 만큼 반복해서 핸들러를 실행하게 함
    }

    private fun autoScrollStop(){
        myHandler.removeMessages(0) // 핸들러를 중지시킴
    }

    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if(msg.what == 0) {
                binding.autoScrollViewPager.setCurrentItem(++currentPosition, true) // 다음 페이지로 이동
                autoScrollStart(intervalTime) // 스크롤을 계속 이어서 한다.
            }
        }
    }

    // 다른 페이지 갔다가 돌아오면 다시 스크롤 시작
    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }

    // 다른 페이지로 떠나있는 동안 스크롤이 동작할 필요는 없음. 정지
    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }

}