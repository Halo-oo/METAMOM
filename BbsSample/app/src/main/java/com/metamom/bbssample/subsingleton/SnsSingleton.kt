package com.metamom.bbssample.subsingleton

import com.metamom.bbssample.sns.SnsDto

class SnsSingleton {

    companion object{
        var code : String? = ""
        var position:Int? = null
        var imageUri:String = ""
        var content:String = ""
        var insertCheck:Int? = null
        var snsDto:SnsDto? = null
    }
}