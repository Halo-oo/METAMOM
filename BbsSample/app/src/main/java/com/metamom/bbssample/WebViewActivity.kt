package com.metamom.bbssample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView.settings.javaScriptEnabled = true   // 자바스크립트 허용
        webView.webViewClient = WebViewClient()     // 웹뷰에서 새 창이 뜨지 않도록 하는 구문
        webView.webChromeClient = WebChromeClient() // 웹뷰에서 새 창이 뜨지 않도록 하는 구문
        webView.loadUrl("https://www.10000recipe.com/recipe/list.html")

    }
}