package com.metamom.bbssample.recipe2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.metamom.bbssample.R

class DetailRecipe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recipe)


        var reciDetailImage = findViewById<ImageView>(R.id.reciDetailImage)
        var reciDetailTitle = findViewById<TextView>(R.id.reciDetailTitle)
        var reciGridView = findViewById<GridView>(R.id.reciGridView)

        // 레시피 가져오기
        val dto = RecipeDto(0, "", "", "", "", "", "", "")
        var recipeList = RecipeDao.getInstance().getRecipe(dto)
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + recipeList?.get(0)?.recipe)


        // MainActivity에서 데이터 가져오기
        val intent = Intent(this.intent)
        val a = intent.getStringExtra("List")
        val b = intent.getIntExtra("Num", 1)

        println("~~~~~~~~~~~~~~~~ number : " + b)


        // 재료 나누기

        var ingredientList = recipeList?.get(b)?.ingredient
        val resultIngredientList: Array<String>? = ingredientList?.split("!")?.toTypedArray()

        var ingredientGridViewList = arrayListOf<RecipeGridViewDto>()

// GridView는 arrayOf 배열로 입력 / recylerView는 ArrayListOf....
        for(i in 0 until resultIngredientList!!.size){
            println("##########################" + resultIngredientList[i])

            ingredientGridViewList.add(RecipeGridViewDto(resultIngredientList[i]))
        }

        reciGridView.setAdapter(
            ArrayAdapter(this, R.layout.recipe_ingredient_item, resultIngredientList)
        )




        // 레시피 세부적으로 나누기

        var reciList = recipeList?.get(b)?.recipe
        var reciImageList = recipeList?.get(b)?.recipeImage

        val reciRecycler: Array<String>? = reciList?.split("!")?.toTypedArray()
        val reciImageRecycler: Array<String>? = reciImageList?.split("/")?.toTypedArray()

        // Array<String>을 Dto로 변형
        var reciRecyclerList = arrayListOf<RecipeRecyclerDto>()

        for(i in 0 until reciRecycler!!.size) {
            println(" $$$$$$$$$$$$$$$$$$$$$$ " + reciRecycler[i])
            println(" $$$$$$$$$$$$$$$$$$$$$$ " +  reciImageRecycler?.get(i))

            reciRecyclerList.add(RecipeRecyclerDto(reciRecycler[i], reciImageRecycler?.get(i)))
            //reciRecyclerList.add(RecipeRecylerDto("", reciImageRecycler?.get(i)))
        }

        println("@@@@@@@@@@@@@@@@@@@@@@@@" + reciRecyclerList)

        // recylerView
        var recyclerView = findViewById<RecyclerView>(R.id.reciRecyclerView)

        val mAdapter = RecipeAdapter(this, reciRecyclerList)
        recyclerView.adapter = mAdapter

        val layout = LinearLayoutManager(this)
        recyclerView.layoutManager = layout

        recyclerView.setHasFixedSize(true)



        reciDetailImage.setImageBitmap(ImageRoader().getBitmapImg( recipeList!![b].foodImage))
        reciDetailTitle.text = recipeList!![b].recipeName

    }
}
