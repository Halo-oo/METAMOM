package com.metamom.bbssample.fragments

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.metamom.bbssample.R
import com.metamom.bbssample.databinding.FragmentRecipeBinding
import com.metamom.bbssample.recipe2.*
import com.metamom.bbssample.recipe2.ImageRoader
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding

lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false)

        val listView = binding.root.findViewById<ListView>(R.id.fragReciView)
        val editText = binding.root.findViewById<EditText>(R.id.fragReciEdit)

        // 레시피 가져오기
        var dto = RecipeDto(0, "", "", "", "", "", "", "")
        var recipeList = RecipeDao.getInstance().getRecipe(dto)
        // println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + recipeList?.get(0))


        // list에 RecipeDto recipeName으로 출력


        var list: ArrayList<String> = ArrayList()
        for (i in 0 until recipeList!!.size) {
            recipeList[i].recipeName?.let { list.add(it) }
        }

        adapter = ArrayAdapter(this.context!!, R.layout.recipe_list, list)
        println("~~~~~~~~~~~~~~~${adapter.getItem(0).toString()}")
        listView.adapter = adapter

        // listview 검색기능!
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable) {
                list!!.clear()

                dto = RecipeDto(0, edit.toString(), "", "", "", "", "", "")
                recipeList = RecipeDao.getInstance().getRecipe(dto)

                for(i in 0 until recipeList!!.size){
                    recipeList!![i].recipeName?.let { list!!.add(it) }
                }

                // list: 검색 후 listView에 표시되는 항목들 출력
                adapter =  ArrayAdapter(activity!!, R.layout.recipe_list, list)

                listView.adapter = adapter

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        // bottomSheet
        val bottomSheetView = layoutInflater.inflate(R.layout.activity_recipe_bottom_sheet, null)
        val bottomSheetDialog = BottomSheetDialog(this.context!!)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

        val reciDetailButton = bottomSheetView.findViewById<Button>(R.id.reciBotBtn)
        val reciBotImage = bottomSheetView.findViewById<ImageView>(R.id.reciBotImage)
        val reciBotTitle = bottomSheetView.findViewById<TextView>(R.id.reciBotTitle)
        val reciBotcontent = bottomSheetView.findViewById<TextView>(R.id.reciBotContent)


        var choicePos = 0
        var findIndex = -1

        // listView 눌려서 bottomSheet 가져오기
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, v: View?, pos: Int, p3: Long) {
                //reciBotTitle.text = recipeList!![pos].recipeName
                reciBotTitle.text = list!![pos]

                println("~~~~~~~~~~~+++++++++++++++++~~~~~~~~~~~" +  list!![pos])
                println("~~~~~~~~~~~+++++++++++++++++~~~~~~~~~~~recipeList!!.size: " +  recipeList!!.size)


                for(i in 0 until recipeList!!.size){

                    if( recipeList!![i].recipeName === list!![pos]){
                        println("++++++++++++++++++++++!!!!!!!!!!!!!!!!!" + recipeList!![i])
                        println("++++++++++++++++++++++!!!!!!!!!!!!!!!!!" + recipeList!![i].recipeSeq)
                        findIndex = i
                        break
                    }
                }
                println("%%%%%%%%%%%%%%%%%%%%%%%%%%%" + recipeList!![findIndex].recipeSeq)

                reciBotImage.setImageBitmap(ImageRoader().getBitmapImg(recipeList!![findIndex].foodImage))

                reciBotcontent.text = recipeList!![findIndex].brief
                bottomSheetDialog.show()
                choicePos =  recipeList!![findIndex].recipeSeq


                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~choicePos : " + choicePos)

            }
        }

        // bottomSheet에 가져간 데이터 짐싸기
        reciDetailButton.setOnClickListener {

            Toast.makeText(this.context!!, "${recipeList!![findIndex].recipeName} 클릭", Toast.LENGTH_SHORT).show()

            println("@@@@@@@@@@@@@@@@@@@@@@ 여긴 뒤쪽 choicePos : " + choicePos)

            // 이동
            //Intent(이동) +  putExtra(짐)
            val intent = Intent(this.context!!, DetailRecipe::class.java)
            //intent.putExtra("List", recipeList!![findIndex])
            intent.putExtra("Num", choicePos - 1)
            startActivity(intent)

        }



        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_recipeFragment_to_homeFragment)
        }

        binding.mealTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_recipeFragment_to_mealFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_recipeFragment_to_talkFragment)
        }

        binding.accountTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_recipeFragment_to_accountFragment)
        }
        return binding.root
    }
}









// 서버에서 이미지 가져오기
class ImageRoader {
    private val serverUrl = "http://14.39.38.168:3000/upload/"

    init {
        ThreadPolicy()
    }

    fun getBitmapImg(imgStr: String?): Bitmap? {
        var bitmapImg: Bitmap? = null
        try {
            val url = URL(
                serverUrl +
                        URLEncoder.encode(imgStr, "utf-8")
            )
            // Character is converted to 'UTF-8' to prevent broken
            val conn = url
                .openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            val `is` = conn.inputStream
            bitmapImg = BitmapFactory.decodeStream(`is`)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmapImg
    }
}

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
class ThreadPolicy {
    // For smooth networking
    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}
/*
       // 이동
       //Intent(이동) +  putExtra(짐)
       val intent = Intent(activity, RecipeMainActivity::class.java)
       startActivity(intent)


 /*   val listView = binding.root.findViewById<ListView>(R.id.reciView)
       val editText = binding.root.findViewById<EditText>(R.id.reciEdit)


       // 레시피 가져오기
       var dto = RecipeDto(0, "", "", "", "", "", "", "")
       var recipeList = RecipeDao.getInstance().getRecipe(dto)
       // println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + recipeList?.get(0))


       // list에 RecipeDto recipeName으로 출력


       var list: ArrayList<String> = ArrayList()
       for (i in 0 until recipeList!!.size) {
           recipeList[i].recipeName?.let { list.add(it) }
       }

//       list.sortWith(Comparator.naturalOrder())

       var adapter = ArrayAdapter(activity!!, R.layout.recipe_list,list)
       //var adapter = ArrayAdapter(this, R.layout.recipe_list, list)

       listView.adapter = adapter


       // listview 검색기능!
       editText.addTextChangedListener(object : TextWatcher {
           override fun afterTextChanged(edit: Editable) {
               list!!.clear()

               dto = RecipeDto(0, edit.toString(), "", "", "", "", "", "")
               recipeList = RecipeDao.getInstance().getRecipe(dto)

               for(i in 0 until recipeList!!.size){
                   recipeList!![i].recipeName?.let { list!!.add(it) }
               }

               // list: 검색 후 listView에 표시되는 항목들 출력
               adapter =  ArrayAdapter(activity!!, R.layout.recipe_list, list)

               listView.adapter = adapter

           }
           override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
           }
           override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
           }
       })



       // bottomSheet
       val bottomSheetView = layoutInflater.inflate(R.layout.activity_recipe_bottom_sheet, null)
       val bottomSheetDialog = BottomSheetDialog(activity!!)
       bottomSheetDialog.setContentView(bottomSheetView)
       bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

       val reciDetailButton = bottomSheetView.findViewById<Button>(R.id.reciBotBtn)
       val reciBotImage = bottomSheetView.findViewById<ImageView>(R.id.reciBotImage)
       val reciBotTitle = bottomSheetView.findViewById<TextView>(R.id.reciBotTitle)
       val reciBotcontent = bottomSheetView.findViewById<TextView>(R.id.reciBotContent)


       var choicePos = 0
       var findIndex = -1



       // listView 눌려서 bottomSheet 가져오기
       listView.onItemClickListener = object : AdapterView.OnItemClickListener {
           override fun onItemClick(parent: AdapterView<*>?, v: View?, pos: Int, p3: Long) {
               //reciBotTitle.text = recipeList!![pos].recipeName
               reciBotTitle.text = list!![pos]

               println("~~~~~~~~~~~+++++++++++++++++~~~~~~~~~~~" +  list!![pos])
               println("~~~~~~~~~~~+++++++++++++++++~~~~~~~~~~~recipeList!!.size: " +  recipeList!!.size)


               for(i in 0 until recipeList!!.size){

                   if( recipeList!![i].recipeName === list!![pos]){
                       println("++++++++++++++++++++++!!!!!!!!!!!!!!!!!" + recipeList!![i])
                       println("++++++++++++++++++++++!!!!!!!!!!!!!!!!!" + recipeList!![i].recipeSeq)
                       findIndex = i
                       break
                   }
               }
               println("%%%%%%%%%%%%%%%%%%%%%%%%%%%" + recipeList!![findIndex].recipeSeq)

               reciBotImage.setImageBitmap(ImageRoader().getBitmapImg(recipeList!![findIndex].foodImage))

               reciBotcontent.text = recipeList!![findIndex].brief
               bottomSheetDialog.show()
               choicePos =  recipeList!![findIndex].recipeSeq


               println("~~~~~~~~~~~~~~~~~~~~~~~~~~~choicePos : " + choicePos)

           }
       }


       // bottomSheet에 가져간 데이터 짐싸기
       reciDetailButton.setOnClickListener {

           Toast.makeText(activity, "${recipeList!![findIndex].recipeName} 클릭", Toast.LENGTH_SHORT).show()

           println("@@@@@@@@@@@@@@@@@@@@@@ 여긴 뒤쪽 choicePos : " + choicePos)

           // 이동
           //Intent(이동) +  putExtra(짐)
           val intent = Intent(activity, DetailRecipe::class.java)
           //intent.putExtra("List", recipeList!![findIndex])
           intent.putExtra("Num", choicePos - 1)
           startActivity(intent)

       }
*/


 /*  // 서버에서 이미지 가져오기
   class ImageRoader {
       private val serverUrl = "http://14.39.38.168:3000/upload/"

       init {
           com.metamom.bbssample.recipe2.ThreadPolicy()
       }

       fun getBitmapImg(imgStr: String?): Bitmap? {
           var bitmapImg: Bitmap? = null
           try {
               val url = URL(
                   serverUrl +
                           URLEncoder.encode(imgStr, "utf-8")
               )
               // Character is converted to 'UTF-8' to prevent broken
               val conn = url
                   .openConnection() as HttpURLConnection
               conn.doInput = true
               conn.connect()
               val `is` = conn.inputStream
               bitmapImg = BitmapFactory.decodeStream(`is`)
           } catch (e: IOException) {
               e.printStackTrace()
           }
           return bitmapImg
       }
   }

   @TargetApi(Build.VERSION_CODES.GINGERBREAD)
   class ThreadPolicy {
       // For smooth networking
       init {
           val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
           StrictMode.setThreadPolicy(policy)
       }
   */
*/
