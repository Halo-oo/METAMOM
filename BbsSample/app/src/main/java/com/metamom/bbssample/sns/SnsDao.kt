package com.metamom.bbssample.sns

import com.metamom.bbssample.MemberDto
import com.metamom.bbssample.RetrofitClient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface SnsService{
    @POST("/snsInsert")
    fun snsInsert(@Body dto:SnsDto): Call<Int?>

    @POST("/snsDelete")
    fun snsDelete(@Query("seq") seq:Int) :Call<Int>

    @POST("/snsUpdate")
    fun snsUpdate(@Body dto:SnsDto) : Call<Int>

    @POST("/snsImgUpdate")
    fun snsImgUpdate(@Body dto:SnsDto) : Call<Int>

    @POST("/snsGetMember")
    fun snsGetMember(@Query("id") id:String):Call<MemberDto>

    @GET("allSns")
    fun allSns() : Call<List<SnsDto>>

    @POST("/snsLikeInsert")
    fun snsLikeInsert(@Body dto:SnsLikeDto) : Call<Int>

    @POST("/snsCommentInsert")
    fun snsCommentInsert(@Body dto:SnsCommentDto) : Call<Int>

    @POST("/snsCommentUpdate")
    fun snsCommentUpdate(@Body dto:SnsCommentDto) : Call<Int>

    @POST("/snsLikeDelete")
    fun snsLikeDelete(@Body dto:SnsLikeDto) : Call<Int>

    @POST("/snsLikeAllDelete")
    fun snsLikeAllDelete(@Query("seq") seq:Int) : Call<Int>

    @POST("/snsLikeCount")
    fun snsLikeCount(@Query("seq") seq:Int) : Call<Int>

    @POST("/snsLikeCheck")
    fun snsLikeCheck(@Body dto:SnsLikeDto) : Call<Int>

    @POST("snsCommentCount")
    fun snsCommentCount(@Query("seq") seq:Int) : Call<Int>

    @POST("/snsCommentAllDelete")
    fun snsCommentAllDelete(@Query("seq") seq:Int) : Call<Int>

    @POST("/snsCommentDelete")
    fun snsCommentDelete(@Query("cmtseq") cmtseq:Int) : Call<Int>

    @GET("allComment")
    fun allComment(@Query("seq") seq:Int) : Call<List<SnsCommentDto>>

    @POST("/nextSeq")
    fun nextSeq() : Call<Int>

    @POST("/currSeq")
    fun currSeq() : Call<Int>





}

class SnsDao {
    companion object {
        var SnsDao: SnsDao? = null
        fun getInstance(): SnsDao {
            if (SnsDao == null) {
                SnsDao = SnsDao()
            }
            return SnsDao!!
        }
    }


    fun snsInsert(dto: SnsDto): Int? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsInsert(dto)
        val response = call?.execute()

        return response?.body()
    }

    fun snsDelete(seq:Int): Int? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsDelete(seq)
        val response = call?.execute()

        return response?.body()
    }

    fun snsUpdate(dto:SnsDto): Int? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsUpdate(dto)
        val response = call?.execute()

        return response?.body()
    }

    fun snsImgUpdate(dto:SnsDto): Int? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsImgUpdate(dto)
        val response = call?.execute()

        return response?.body()
    }

    fun snsCommentInsert(dto:SnsCommentDto) : Int? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsCommentInsert(dto)
        val response = call?.execute()
        return response?.body()
    }

    fun snsCommentDelete(cmtseq:Int): Int? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsCommentDelete(cmtseq)
        val response = call?.execute()

        return response?.body()
    }

    fun snsCommentUpdate(dto:SnsCommentDto): Int? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsCommentUpdate(dto)
        val response = call?.execute()

        return response?.body()
    }


    fun snsGetMember(id:String):MemberDto{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsGetMember(id)
        val response = call?.execute()

        return response?.body() as MemberDto
    }

    fun allSns() : ArrayList<SnsDto>?{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.allSns()
        val response = call?.execute()

        return response?.body() as ArrayList<SnsDto>
    }

    fun snsLikeInsert(dto:SnsLikeDto):Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsLikeInsert(dto)
        val response = call?.execute()

        return response?.body()!!
    }

    fun snsLikeDelete(dto:SnsLikeDto) : Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsLikeDelete(dto)
        val response = call?.execute()

        return response?.body()!!
    }

    fun snsLikeAllDelete(seq:Int) : Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsLikeAllDelete(seq)
        val response = call?.execute()

        return response?.body()!!
    }


    fun snsLikeCount(seq:Int) :Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsLikeCount(seq)
        val response = call?.execute()

        return response?.body()!!
    }

    fun snsCommentCount(seq:Int) :Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsCommentCount(seq)
        val response = call?.execute()

        return response?.body()!!
    }

    fun snsCommentAllDelete(seq:Int) : Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsCommentAllDelete(seq)
        val response = call?.execute()

        return response?.body()!!
    }

    fun snsLikeCheck(dto:SnsLikeDto): Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.snsLikeCheck(dto)
        val response = call?.execute()

        return response?.body()!!
    }

    fun allComment(seq:Int) : ArrayList<SnsCommentDto>{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.allComment(seq)
        val response = call?.execute()

        return response?.body() as ArrayList
    }

    fun nextSeq() : Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.nextSeq()
        val response = call?.execute()

        return response?.body()!!
    }

    fun currSeq() : Int{
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(SnsService::class.java)
        val call = service?.currSeq()
        val response = call?.execute()

        return response?.body()!!
    }
}
