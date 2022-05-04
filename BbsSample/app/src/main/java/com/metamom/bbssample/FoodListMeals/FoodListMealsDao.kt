package com.metamom.bbssample.FoodListMeals


import com.metamom.bbssample.MemberDao
import com.metamom.bbssample.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface FoodListMealsService{
    //write 보내기
    @POST("/FoodListTest")
    fun FoodListTest(@Body dto:FoodListMealsDto) : Call<String>

    //아이디값일치 리스트 받기
    @GET("/FoodListSelect")
    fun FoodListSelect(@Query("id")id:String):Call<List<FoodListMealsDto>>
    //날짜별로 받기
    //날짜 아이디 비교 리스트 받기
    @GET("/FoodListSelectDay")
    fun FoodListSelectDay(@Query("wdate")wdate:String,@Query("id")id:String):Call<List<FoodListMealsDto>>

    @GET("/checkId")
    fun checkId(@Query("id")id:String):Call<String>

    @Headers("Content-Type: application/json")
    @POST("/deleteFoodList")
    fun deleteFoodList(@Body seqfoodlist:Int):Call<String>

    @POST("/updateFoodList")
    fun updateFoodList(@Body dto:FoodListMealsDto):Call<String>

    //seq 일치 디테일 불러오기
    //@GET("/detailSelect")
   // fun detailSelect(@Query("seqfoodlist")seqfoodlist: Int):Call<List<FoodListMealsDto>>

}
class FoodListMealsDao {
    companion object {
        var foodListMealsDao: FoodListMealsDao? = null

        fun getInstance(): FoodListMealsDao {
            if (foodListMealsDao == null) {
                foodListMealsDao = FoodListMealsDao()
            }
            return foodListMealsDao!!
        }
    }
    fun updateFoodList(dto:FoodListMealsDto):String{
        println("111111111: setOnClickListener")
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(FoodListMealsService::class.java)
        val call = service?.updateFoodList(dto)
        val response = call?.execute()

        return response?.body() as String
    }
    fun deleteFoodList(seqfoodlist: Int):String{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(FoodListMealsService::class.java)
        //println("~~~~~~~~~" + seqfoodlist) 확인용
        val call = service?.deleteFoodList(seqfoodlist)
        val response = call?.execute()

        return response?.body() as String
    }

    fun FoodListTest(dto: FoodListMealsDto): String {
        val retrofit = RetrofitClient.getInstance()

        val service = retrofit?.create(FoodListMealsService::class.java)
        val call = service?.FoodListTest(dto)
        val response = call?.execute()

        return response?.body() as String

    }
    fun FoodListSelect(id:String):ArrayList<FoodListMealsDto>{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(FoodListMealsService::class.java)
        val call = service?.FoodListSelect(id)
        val response = call?.execute()

        return response?.body() as ArrayList<FoodListMealsDto>
    }

    fun FoodListSelectDay(wdate:String,id:String):ArrayList<FoodListMealsDto> {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(FoodListMealsService::class.java)
        val call = service?.FoodListSelectDay(wdate,id)
        val response = call?.execute()
        return response?.body() as ArrayList<FoodListMealsDto>
    }

    fun checkId(id:String):String{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(FoodListMealsService::class.java)
        val call = service?.checkId(id)
        val response = call?.execute()
        return response?.body() as String
    }
}