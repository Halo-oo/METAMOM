package com.metamom.bbssample.subsingleton

class SubPurchaseSingleton {

    /* #21# 구독 신청 시 결제기능을 위해 사용 */
    companion object {
        var subPeriod :Int? = null              // 신청한 구독 개월
        //var subPurchase :String? = null         // Y == 구매성공, N == 구매실패 > 사용안함

        override fun toString(): String {
            return "SubPurchaseSingleton(subPeriod=$subPeriod)"
        }
    }
}