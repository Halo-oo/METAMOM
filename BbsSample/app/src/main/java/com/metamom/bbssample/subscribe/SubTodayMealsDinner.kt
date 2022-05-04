package com.metamom.bbssample.subscribe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metamom.bbssample.MainButtonActivity
import com.metamom.bbssample.R
import com.metamom.bbssample.fragments.MealFragment
import com.metamom.bbssample.subsingleton.MemberSingleton
import com.metamom.bbssample.subsingleton.SubTodayMealSingleton

class SubTodayMealsDinner : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_today_meals_dinner)

        /* #21# 뒤로가기(이전화면) > Fragment로 이사하면서 뒤로가기 Button 없앰 */
        /* 액션바 설정 */
        setSupportActionBar(findViewById(R.id.subDinner_toolbar))
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            // back button 커스텀
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        findViewById<Toolbar>(R.id.subDinner_toolbar).setNavigationOnClickListener {
            onBackPressed()
        }

        /* 식단 추천 흐름 (자세한 사항은 Morining.kt 파일 참고)
           - 고쳐야 하는 부분의 code는 Bookmark 표시 해두었음
        *  1) 식단 추천 이력 확인
        *     Y > 추천하였던 식단 불러와서 RecyclerView에 드로잉
        *     N > 2번으로 이동
        *  2) 구독 유형에 따라 식단을 Random으로 1개 불러와서 RecyclerView에 드로잉
        *  2-1) 불러온 식단 정보는 식단 추천 이력 확인을 위하여 REMEBER TABLE에 저장 */

        var todayMeal: Any? = null
        var recyclerView = findViewById<RecyclerView>(R.id.subDinner_recyclerView)

        /* #21# 오늘의 식단 추천 이력 확인 */
        val checkLogMeal = SubscribeDao.getInstance().subLogCheckMeal(SubMealRememberDto(0, "", MemberSingleton.id, "", SubTodayMealSingleton.time!!, SubTodayMealSingleton.type!!))
        Log.d("", "#21# checkLogMeal 값 확인 > ${checkLogMeal.toString()}")

        // 1) 식단 추천 이력이 있을 경우
        if (checkLogMeal != null){
            /* #21# 추천하였던 식단 불러오기 */
            if (SubTodayMealSingleton.type == 0){           // [다이어트] 식단
                todayMeal = SubscribeDao.getInstance().subDietMeal(checkLogMeal.subremSeq)
            }
            else if (SubTodayMealSingleton.type == 1){      // [운동] 식단
                todayMeal = SubscribeDao.getInstance().subExerMeal(checkLogMeal.subremSeq)
            }
        }
        // 2) 식단 추천 이력이 없을 경우
        else {
            /* #21# 오늘의 식단 추천 및 recyclerView 드로잉 (+ 추천한 식단 저장) */
            Log.d("SubTodayMealsDinner", "#21# SubTodayMealSingleton 확인 > ${SubTodayMealSingleton.toString()}")

            // 2-i) [다이어트] 회원이 다이어트 식단을 신청하였을 경우 (type == 0)
            if (SubTodayMealSingleton.type == 0) {
                Log.d("SubTodayMealsDinner", "#21# 오늘의 식단[아침] *다이어트 식단* 가져오기 실행")

                // 2-ii) 다이어트 식단 가져오기
                todayMeal = SubscribeDao.getInstance().subRandomDietMeal(SubDietMealDto(0, SubTodayMealSingleton.time!!, "", "", SubTodayMealSingleton.dinnerKcal!!.toDouble(), SubTodayMealSingleton.type.toString(), MemberSingleton.id))
                // 2-iv) 추천한 식단 정보 DB TABLE에 저장하기
                if (todayMeal != null) {
                    val rememberMeal = SubscribeDao.getInstance().subMealRemember(SubMealRememberDto((todayMeal as SubDietMealDto).subdfSeq, (todayMeal as SubDietMealDto).subdfName, MemberSingleton.id, "", (todayMeal as SubDietMealDto).subdfTime, SubTodayMealSingleton.type!!))
                    if (rememberMeal == "Success") Log.d("SubTodayMealsDinner", "#21# 추천한 다이어트 식단 REMEMBER TABLE 내 저장완료")
                }
                else {
                    Toast.makeText(this, "관리자에게 문의해주시길 바랍니다. 죄송합니다", Toast.LENGTH_LONG).show()
                    Log.d("SubTodayMealsLunch", "#21# 오늘의 식단 [저녁] *다이어트* 해당되는 식단 없음(null) Error 발생")

                    val i = Intent(this, MainButtonActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
            }

            // 2-i) [운동] 회원이 운동 식단을 신청하였을 경우 (type == 1)
            else if (SubTodayMealSingleton.type == 1) {
                Log.d("SubTodayMealsDinner", "#21# 오늘의 식단[아침] *운동 식단* 가져오기 실행")

                // 2-ii) 운동 식단 가져오기
                todayMeal = SubscribeDao.getInstance().subRandomExerMeal(SubExerMealDto(0, SubTodayMealSingleton.time!!, "", "", SubTodayMealSingleton.morningKcal!!.toDouble(), SubTodayMealSingleton.type.toString(), MemberSingleton.id))
                // 2-iv) 추천한 식단 정보 DB TABLE에 저장하기
                if (todayMeal != null) {
                    val rememberMeal = SubscribeDao.getInstance().subMealRemember(SubMealRememberDto((todayMeal as SubExerMealDto).subefSeq, (todayMeal as SubExerMealDto).subefName, MemberSingleton.id, "", (todayMeal as SubExerMealDto).subefTime, SubTodayMealSingleton.type!!))
                    if (rememberMeal == "Success") Log.d("SubTodayMealsDinner", "#21# 추천한 운동 식단 REMEMBER TABLE 내 저장완료")
                }
                else {
                    Toast.makeText(this, "관리자에게 문의해주시길 바랍니다. 죄송합니다", Toast.LENGTH_LONG).show()
                    Log.d("SubTodayMealsLunch", "#21# 오늘의 식단 [저녁] *운동* 해당되는 식단 없음(null) Error 발생")

                    val i = Intent(this, MainButtonActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
            }
        }

        // 3) 전달받은 식단을 RecyclerView에 드로잉하기
        if (todayMeal != null){
            Log.d("SubTodayMealsDinner", "#21# Back으로부터 전달받은 오늘의 식단 > ${todayMeal.toString()}")

            val subCustomAdapter = SubCustomAdapter(this, todayMeal!!)
            recyclerView.adapter = subCustomAdapter

            val layout = LinearLayoutManager(this)      // 세로모드
            recyclerView.layoutManager = layout
            recyclerView.setHasFixedSize(true)
        }
        else {
            Toast.makeText(this, "관리자에게 문의해주시길 바랍니다. 죄송합니다", Toast.LENGTH_LONG).show()
        }
    }
}