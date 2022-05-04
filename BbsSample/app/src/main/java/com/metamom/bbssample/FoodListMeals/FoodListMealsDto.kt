package com.metamom.bbssample.FoodListMeals


import android.os.Parcel
import android.os.Parcelable

class FoodListMealsDto(val id:String?,val seqfoodlist:Int,val wdate:String?,val meals:String?,val memo:String?,val imgUrl:String?,val foodscore:String?,val del:Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ){}
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(seqfoodlist)
        parcel.writeString(wdate)
        parcel.writeString(meals)
        parcel.writeString(memo)
        parcel.writeString(imgUrl)
        parcel.writeString(foodscore)
        parcel.writeInt(del)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodListMealsDto> {
        override fun createFromParcel(parcel: Parcel): FoodListMealsDto {
            return FoodListMealsDto(parcel)
        }

        override fun newArray(size: Int): Array<FoodListMealsDto?> {
            return arrayOfNulls(size)
        }
    }


}