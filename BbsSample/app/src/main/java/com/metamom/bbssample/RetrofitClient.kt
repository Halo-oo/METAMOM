package com.metamom.bbssample

import android.os.StrictMode
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager

class RetrofitClient {
    companion object {
        private var instance: Retrofit? = null

        // 쿠키 유지
        private var client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()

        fun getInstance(): Retrofit? {
            if(instance == null) {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                val gson = GsonBuilder().setLenient().create()

                // local 주소 - 46번째줄 부분도 바꿔주세요!
                // 박해빈 : 192.168.35.64
                // 양성훈 : http://192.168.0.29:3000/
                // 엄희정 : http://172.30.1.25:3000/ > [04.05 변경] 172.30.1.51
                // 이선행 : http://192.168.219.111:3000/
                // 최재석 :http://14.39.38.168:3000/
                instance = Retrofit.Builder()
                    .baseUrl("http://192.168.219.111:3000/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
            }
            return instance!!
        }

        // 문자열을 전송받을 시에 사용한다 → #21# 사용 x 권장
        /*private var instanceStr: Retrofit? = null
        fun getInstanceStr(): Retrofit? {
            if(instanceStr == null) {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                // ScalarsConverterFactory 만을 사용해야 한다
                instanceStr = Retrofit.Builder()
                    .baseUrl("http://192.168.35.64:3000/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()//주석
            }
            return instanceStr!!
        }*/

    }
}