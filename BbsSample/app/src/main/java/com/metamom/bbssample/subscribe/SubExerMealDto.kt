package com.metamom.bbssample. subscribe

import android.os.Parcel
import android.os.Parcelable

/* #21# 구독 _오늘의 식단 [운동] RecyclerView에 드로잉하기 위하여 파일 사용 */
// (04.05) 음식 양 제외
class SubExerMealDto(val subefSeq:Int, val subefTime:Int, val subefImage:String?, val subefName:String?, val subefKcal:Double/*, val subefAmount:Int*/, val subefType:String?, val subefID:String?) : Parcelable {

    // parcel에 대한 기본생성자
    constructor(parcel: Parcel) :this(
        parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readDouble()/*, parcel.readInt()*/, parcel.readString(), parcel.readString()
    ){ }

    override fun describeContents(): Int {
        return 0
    }

    // 실제 object를 serializtion/flattening하는 메소드
    /*parcel.writeInt(subefAmount)*/
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(subefSeq)
        parcel.writeInt(subefTime)
        parcel.writeString(subefImage)
        parcel.writeString(subefName)
        parcel.writeDouble(subefKcal)
        parcel.writeString(subefType)
        parcel.writeString(subefID)
    }

    companion object CREATOR : Parcelable.Creator<SubExerMealDto> {
        override fun createFromParcel(parcel: Parcel): SubExerMealDto {
            return SubExerMealDto(parcel)
        }

        override fun newArray(size: Int): Array<SubExerMealDto?> {
            return arrayOfNulls(size)
        }
    }

    /*, subefAmount=${subefAmount}*/
    override fun toString(): String {
        return "SubExerMealDto(subefSeq=${subefSeq}, subefTime=${subefTime}, subefImage=${subefImage}, subefName=${subefName}, subefKcal=${subefKcal}, subefType=${subefType}, subefID=${subefID}"
    }

}