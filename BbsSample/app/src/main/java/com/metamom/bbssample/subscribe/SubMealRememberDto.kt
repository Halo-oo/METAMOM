package com.metamom.bbssample.subscribe

import android.os.Parcel
import android.os.Parcelable

/* #21# 추천하였던 오늘의 식단 관리를 위해 사용하는 Dto */
class SubMealRememberDto(val subremSeq:Int, val subremName:String?, val subremId:String?, val subremDate:String?, val subremMealstime:Int, val subremType:Int) :Parcelable {

    // parcel에 대한 기본생성자
    constructor(parcel: Parcel) :this(
        parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt()
    ){ }

    override fun describeContents(): Int {
        return 0
    }

    // 실제 object를 serializtion/flattening하는 메소드
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(subremSeq)
        parcel.writeString(subremName)
        parcel.writeString(subremId)
        parcel.writeString(subremDate)
        parcel.writeInt(subremMealstime)
        parcel.writeInt(subremType)
    }

    companion object CREATOR : Parcelable.Creator<SubMealRememberDto> {
        override fun createFromParcel(parcel: Parcel): SubMealRememberDto {
            return SubMealRememberDto(parcel)
        }

        override fun newArray(size: Int): Array<SubMealRememberDto?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "SubMealRememberDto(subremSeq=$subremSeq, subremName=$subremName, subremId=$subremId, subremDate=$subremDate, subremMealstime=$subremMealstime, subremType=$subremType)"
    }
}