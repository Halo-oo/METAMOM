package com.metamom.bbssample

import android.util.Log
import com.metamom.bbssample.subscribe.SubscribeService
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Member

interface MemberService {

    // 7. 문자 보내기/ list 받기
    //@FormUrlEncoded
    @POST("/login")
    fun login(@Body dto: MemberDto): Call<MemberDto>

    @POST("/addmember")
    fun addmember(@Body dto: MemberDto): Call<String>

    /* #21# ID 중복체크 */
    @GET("/idCheck")
    fun idCheck(@Query("id") id: String) :Call<Boolean>
    /*@POST("/idCheck")
    fun idCheck(@Body id: String) :Call<Boolean>*/
    
    @POST("/searchId")
    fun searchId(@Body dto : MemberDto) : Call<String>

    @POST("/searchPwd")
    fun searchPwd(@Body dto: MemberDto) : Call<String>


    /* #21# 회원정보 수정 */
    @POST("/userUpdate")
    fun userUpdate(@Body dto: MemberDto) :Call<Boolean>
}


class MemberDao {

    // MemberDao의 싱글톤
    companion object {
        var memberdao: MemberDao? = null
        var user: MemberDto? = null

        fun getInstance(): MemberDao {
            if (memberdao == null) {
                memberdao = MemberDao()
            }
            return memberdao!!
        }
    }

    // list 받기
    fun login(dto : MemberDto) : MemberDto? {

        var response: Response<MemberDto>? = null
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.login(dto)
            response = call?.execute()
        }catch (e:Exception){
            response = null
        }
        if(response == null) return null

        return response?.body() as MemberDto
    }

    fun addmember(dto : MemberDto) : String? {

        var response: Response<String>?
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.addmember(dto)
            response = call?.execute()

        }catch (e:Exception){
            response = null
        }
        if(response == null) return null

        return response?.body() as String
    }

    /* #21# ID 중복체크 */
    fun idCheck(id: String) :Boolean? {
        Log.d("MemberDao", "#21# MemberDao idCheck() 중복체크할 ID값 > $id")

        var response: Response<Boolean>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.idCheck(id)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("MemberDao", "#21# MemberDao idCheck() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as Boolean
    }

    /* #21# 회원정보 수정 */
    fun userUpdate(dto :MemberDto) :Boolean? {
        Log.d("MemberDao", "#21# MemberDao userUpdate() 회원수정을 위한 값 > ${dto.toString()}")

        var response: Response<Boolean>? = null

        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.userUpdate(dto)
            response = call?.execute()
        }
        catch (e:Exception) {
            Log.d("MemberDao", "#21# MemberDao userUpdate() try..catch 오류 > ${e.printStackTrace()}")
            response = null
        }

        if (response == null) return null
        return response.body() as Boolean
    }
//해빈추가 아이디 찾기
    fun searchId(dto : MemberDto) :String?{
        var response:Response<String>? = null
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.searchId(dto)

            response = call?.execute()
        }catch (e:InvocationTargetException){
            println(e.targetException.printStackTrace())
        }
        return response?.body() as String
    }

    //비밀번호 찾기
    fun searchPwd(dto: MemberDto):String?{

        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(MemberService::class.java)
        val call = service?.searchPwd(dto)
        var response = call?.execute()
        return response?.body() as String
    }
}

