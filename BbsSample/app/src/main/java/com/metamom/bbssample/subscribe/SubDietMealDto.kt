package com.metamom.bbssample.subscribe

import android.os.Parcel
import android.os.Parcelable

/* #21# 구독 _오늘의 식단 [다이어트] RecyclerView에 드로잉하기 위하여 파일 사용 */
// subdfType = 타입
// subdfID = 현재 로그인한 사용자 id, 중복되지 않는 식단 추천을 위하여 사용
// (04.05) 음식 양 제외
class SubDietMealDto(val subdfSeq:Int, val subdfTime:Int, val subdfImage:String?, val subdfName:String?, val subdfKcal:Double/*, val subdfAmount:Int*/, val subdfType:String?, val subdfID:String?) :Parcelable {

    // parcel에 대한 기본생성자
    constructor(parcel: Parcel) :this(
        parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readDouble()/*, parcel.readInt()*/, parcel.readString(), parcel.readString()
    ){ }

    override fun describeContents(): Int {
        return 0
    }

    // 실제 object를 serializtion/flattening하는 메소드
    //parcel.writeInt(subdfAmount)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(subdfSeq)
        parcel.writeInt(subdfTime)
        parcel.writeString(subdfImage)
        parcel.writeString(subdfName)
        parcel.writeDouble(subdfKcal)
        parcel.writeString(subdfType)
        parcel.writeString(subdfID)
    }

    companion object CREATOR : Parcelable.Creator<SubDietMealDto> {
        override fun createFromParcel(parcel: Parcel): SubDietMealDto {
            return SubDietMealDto(parcel)
        }

        override fun newArray(size: Int): Array<SubDietMealDto?> {
            return arrayOfNulls(size)
        }
    }

    /*, subdfAmount=${subdfAmount}*/
    override fun toString(): String {
        return "SubDietMealDto(subdfSeq=${subdfSeq}, subdfTime=${subdfTime}, subdfImage=${subdfImage}, subdfName=${subdfName}, subdfKcal=${subdfKcal}, subdfType=${subdfType}, subdfID=${subdfID}"
    }
}