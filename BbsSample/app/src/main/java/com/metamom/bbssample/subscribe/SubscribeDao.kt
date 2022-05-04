package com.metamom.bbssample.subscribe

import android.os.StrictMode
import android.util.Log
import com.metamom.bbssample.RetrofitClient
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

/* #21# [구독] Dao */

interface SubscribeService {

    /* 구독 회원정보 가져오기 _getSubInfo(String id) → return Dto */
    @GET("/getSubInfo")
    fun getSubInfo(@Query("id") id:String): Call<SubscribeDto>

    /* 구독 회원추가 _Back subAdd(SubscribeDto) → return String */
    @Headers("Content-Type: application/json")
    @POST("/subAdd")
    fun subAdd(@Body dto: SubscribeDto) :Call<String>

    /* 구독 만료확인 _Back subEnddayCheck(SubscribeDto dto) → return String */
    @Headers("Content-Type: application/json")
    @POST("/subEnddayCheck")
    fun subEnddayCheck(@Body dto: SubscribeDto) :Call<String>

    /* 구독 오늘의 다이어트 식단 _Back subRandomDietMeal(SubDietMealDto dto) → return SubDietMealDto */
    @Headers("Content-Type: application/json")
    @POST("/subRandomDietMeal")
    fun subRandomDietMeal(@Body dto: SubDietMealDto) :Call<SubDietMealDto>
    
    /* 구독 오늘의 운동 식단 _Back subRandomDietMeal(SubExerMealDto dto) → return SubExerMealDto */
    @Headers("Content-Type: application/json")
    @POST("/subRandomExerMeal")
    fun subRandomExerMeal(@Body dto: SubExerMealDto) :Call<SubExerMealDto>

    /* 구독 추천한 식단 REMEMBER TABLE에 저장 */
    @Headers("Content-Type: application/json")
    @POST("/subMealRemember")
    fun subMealRemember(@Body dto: SubMealRememberDto) :Call<String>

    /* 구독 추천한 오늘의 식단 여부 확인 */
    @Headers("Content-Type: application/json")
    @POST("/subLogCheckMeal")
    fun subLogCheckMeal(@Body dto: SubMealRememberDto) :Call<SubMealRememberDto>

    /* 구독 추천하였던 *[다이어트]* 식단 가져오기 */
    @Headers("Content-Type: application/json")
    @POST("/subDietMeal")
    fun subDietMeal(@Body subDietSeq: Int) :Call<SubDietMealDto>

    /* 구독 추천하였던 *[운동]* 식단 가져오기 */
    @Headers("Content-Type: application/json")
    @POST("/subExerMeal")
    fun subExerMeal(@Body subExerSeq: Int) :Call<SubExerMealDto>
    /*@GET("/subExerMeal")
    fun subExerMeal(@Query("subExerSeq") subExerSeq: Int) :Call<SubExerMealDto>*/

    /* 구독 추천하였던 식단 중 3일이상인 식단 제거 */
    @GET("/subRememberDel")
    fun subRememberDel(@Query("subDelRemId") subDelRemId: String) :Call<Int>
}



class SubscribeDao {

    companion object {
        var subscribedao: SubscribeDao? = null
        var subUser: SubscribeDto? = null

        fun getInstance(): SubscribeDao {
            if (subscribedao == null){
                subscribedao = SubscribeDao()
            }
            return subscribedao!!
        }
    }


    /* #21# 구독 회원정보 가져오기 */
    fun getSubInfo(id :String) :SubscribeDto? {
        Log.d("SubscribeDao", "#21# SubscribeDao getSubInfo() 동작")
        //Log.d("SubscribeDao", "#21# 현재 로그인한 사용자 id: ${id}")

        var response: Response<SubscribeDto>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.getSubInfo(id)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao getSubInfo() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as SubscribeDto
    }


    /* #21# 구독 회원추가 (+ 멤버 DB 구독값 1로 수정 _#Back에서 처리) */
    fun subAdd(dto :SubscribeDto) :String? {
        Log.d("SubscribeDao", "#21# 구독 신청을 위하여 Front에서 입력받은 Dto 값: ${dto.toString()}")

        var response: Response<String>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subAdd(dto)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subAdd() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as String
    }

    /* #21# 구독 만료여부 확인 (+ 만료 시 멤버 DB 구독값 0으로 수정 __#Back에서 처리) */
    fun subEnddayCheck(dto: SubscribeDto) :String? {
        Log.d("SubscribeDao", "#21# 구독 만료여부 확인을 위하여 Front에서 전달받은 Dto 값: ${dto.toString()}")

        var response :Response<String>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subEnddayCheck(dto)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subEnddayCheck() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }
        Log.d("SubscribeDao", "#21# SubscribeDao subEnddayCheck() 성공 여부 > ${response?.body()}")

        if (response == null) return null
        return response.body() as String
    }


    /* 구독 오늘의 식단 [다이어트] */
    fun subRandomDietMeal(dto: SubDietMealDto) :SubDietMealDto? {
        Log.d("SubscribeDao", "#21# SubscribeDao subRandomDietMeal() 오늘의 식단 #Front에서 전달받은 값 SubTodayMealDto > ${dto.toString()}")

        var response: Response<SubDietMealDto>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subRandomDietMeal(dto)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subRandomDietMeal() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as SubDietMealDto
    }

    /* 구독 오늘의 식단 [운동] */
    fun subRandomExerMeal(dto: SubExerMealDto) :SubExerMealDto? {
        Log.d("SubscribeDao", "#21# SubscribeDao subRandomExerMeal() 오늘의 식단 #Front에서 전달받은 값 SubTodayMealDto > ${dto.toString()}")

        var response: Response<SubExerMealDto>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subRandomExerMeal(dto)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subRandomExerMeal() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as SubExerMealDto
    }

    /* 구독 추천한 오늘의 식단 정보 저장(추가) */
    fun subMealRemember(dto: SubMealRememberDto) :String? {
        Log.d("SubscribeDao", "#21# SubscribeDao subMealRemember() 추천한 오늘의 식단 #Front에서 전달받은 값 SubMealRememberDto > ${dto.toString()}")

        var response: Response<String>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subMealRemember(dto)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subMealRemember() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as String
    }

    /* 구독 추천한 오늘의 식단 이력 여부 확인 */
    // java.io.EOFException: End of input at line 1 column 1 path $ > Error가 나는 경우 결과값이 null이라서 나는 Error이다. (동작에 문제없음)
    fun subLogCheckMeal(dto: SubMealRememberDto) :SubMealRememberDto? {
        Log.d("SubscribeDao", "#21# SubscribeDao subLogCheckMeal() 추천한 오늘의 식단 존재여부 확인 [검색조건 SubMealRememberDto] > ${dto.toString()}")

        var response: Response<SubMealRememberDto>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subLogCheckMeal(dto)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subLogCheckMeal() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as SubMealRememberDto
    }

    /* 구독 추천하였던 *[다이어트]* 식단 가져오기 */
    // (03.29) REMEMBER TABLE에 SEQ가 기본키가 아니라서 ID값도 같이 넘겨주는 것으로 변경 (+운동도 동일하게 변경)
    fun subDietMeal(subDietSeq: Int) :SubDietMealDto? {
        Log.d("SubscribeDao", "#21# SubscribeDao subDietMeal() 추천했던 다이어트 식단 가져오기 SEQ번호 > $subDietSeq")

        var response: Response<SubDietMealDto>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subDietMeal(subDietSeq)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subDietMeal() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as SubDietMealDto
    }

    /* 구독 추천하였던 *[운동]* 식단 가져오기 */
    fun subExerMeal(subExerSeq: Int) :SubExerMealDto? {
        Log.d("SubscribeDao", "#21# SubscribeDao subExerMeal() 추천했던 운동 식단 가져오기 SEQ번호 > $subExerSeq")

        var response: Response<SubExerMealDto>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subExerMeal(subExerSeq)
            Log.d("테스트", "${call}")
            response = call?.execute()
            Log.d("테스트", "${response}")
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subExerMeal() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as SubExerMealDto
    }
    
    /* 구독 추천하였던 식단 중 3일이상 식단 제거 */
    fun subRememberDel(subDelRemId: String) :Int? {
        Log.d("SubscribeDao", "#21# SubscribeDao subRememberDel() 삭제할 식단의 ID값 > $subDelRemId")

        var response: Response<Int>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(SubscribeService::class.java)
            val call = service?.subRememberDel(subDelRemId)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("SubscribeDao", "#21# SubscribeDao subRememberDel() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as Int
    }

}
