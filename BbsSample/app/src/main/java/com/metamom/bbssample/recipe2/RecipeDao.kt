package com.metamom.bbssample.recipe2

import com.metamom.bbssample.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface  RecipeService{
    // test
    @GET("/test")
    fun test(): Call<String>

    @POST("/getRecipe")
    //fun getRecipe(@Body dto:RecipeDto): Call <List<RecipeDto>>
    fun getRecipe(@Body dto:RecipeDto): Call <List<RecipeDto>>
}

class RecipeDao {

    companion object{
        var recipeDao:RecipeDao? = null

        fun getInstance():RecipeDao{
            if (recipeDao == null){
                recipeDao = RecipeDao()
            }
            return recipeDao!!
        }
    }

    // 레시피 받아오기!
    // fun getRecipe(dto: RecipeDto) : List<RecipeDto>?{
    // fun getRecgetRecipeipe():RecipeDto?{
    fun getRecipe(dto:RecipeDto): ArrayList<RecipeDto>?{

/*
        var response: Response<List<RecipeDto>>? = null
        try{
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(RecipeService::class.java)
            val call = service?.getRecipe(dto)
            //val call = service?.getRecipe()
            response = call?.execute()
        }catch (e:Exception){
            response = null
        }
        if(response == null) return null
*/
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(RecipeService::class.java)
        //println("~~~~~~~~~" + seqfoodlist) 확인용
        val call = service?.getRecipe(dto)
        val response = call?.execute()

        return response?.body() as ArrayList<RecipeDto>
    }

}