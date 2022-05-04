package com.metamom.bbssample
import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "b6642e066e3aef02a91748e1fddbfb6c")
    }
}