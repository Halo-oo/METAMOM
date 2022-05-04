package com.metamom.bbssample.FoodListMeals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metamom.bbssample.R
import com.metamom.bbssample.subsingleton.MemberSingleton
import kotlinx.android.synthetic.main.activity_food_list_meals.*
import kotlinx.android.synthetic.main.activity_food_list_meals.view.*
import java.text.SimpleDateFormat
import java.util.*

//나의 식단 기록 아침/점심/저녁
class FoodListMeals : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list_meals)

        var cal = findViewById<CalendarView>(R.id.calendar)
        val addMealsBtn = findViewById<ImageView>(R.id.addMealsBtn)
        var recyclerView = findViewById<RecyclerView>(R.id.FoodMealsList)
        val FoodList = FoodListMealsDao.getInstance().FoodListSelect(MemberSingleton.id!!)
        var clickDay = findViewById<TextView>(R.id.clickDay)
        val myFoodListBtn = findViewById<TextView>(R.id.myFoodListBtn)

        cal.setOnDateChangeListener {cal,year,month,day ->
            clickDay.text = "$year-${month+1}-$day"
        }

        //val checkid = FoodListMealsDao.getInstance().checkId(MemberSingleton.id!!) 아이디만 select
        val foodAdapter = FoodListAdapter(this, FoodList)
        recyclerView.adapter = foodAdapter
        val layout = LinearLayoutManager(this)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)

        myFoodListBtn.setOnClickListener {
            val FoodListDay=FoodListMealsDao.getInstance().FoodListSelectDay(clickDay.text.toString(),MemberSingleton.id!!)
            val foodAdapter = FoodListAdapter(this, FoodListDay)
            recyclerView.adapter = foodAdapter
            val layout = LinearLayoutManager(this)
            recyclerView.layoutManager = layout
            recyclerView.setHasFixedSize(true)
        }

        addMealsBtn.setOnClickListener {
            val i = Intent(this, AddFoodList::class.java)
            startActivity(i)
        }
    }
}