package com.metamom.bbssample.subsingleton

class SubAddSingleton {

    /* #21# 구독 신청 시 선택한 신청정보 저장 */
    companion object {
        var subType :Int? = null
        var subPeriod :Int? = null
        var subMorning :Int = 0
        var subLunch :Int = 0
        var subDinner :Int = 0
        var subSnack :Int = 0

        override fun toString(): String {
            return "SubAddSingleton(subType=$subType, subPeriod=$subPeriod, subMorning=$subMorning, subLunch=$subLunch, subDinner=$subDinner, subSnack=$subSnack)"
        }
    }
}