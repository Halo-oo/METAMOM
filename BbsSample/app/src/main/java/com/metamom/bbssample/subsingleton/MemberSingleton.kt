package com.metamom.bbssample.subsingleton


class MemberSingleton {

    /* #21# 로그인한 사용자의 정보를 저장해두기 위하여 생성 (cuz, 구독 신청 시 구독여부 check)
    *  - Back _login(MemberDto dto) -> return MemberDto */
    companion object {
        var id :String? = null
        var nickname:String? = null
        var profile:String? = null
        var subscribe :String? = null
        var height :Double? = null
        var weight :Double? = null

        override fun toString(): String {
            return "MemberDto(id=$id, nickname=$nickname, profile=$profile, subscribe=$subscribe, height=$height, weight=$weight)"
        }
    }


}