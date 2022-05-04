package com.metamom.bbssample.recipe2

import android.os.Parcel
import android.os.Parcelable

class RecipeDto(val recipeSeq:Int, val recipeName:String?, val ingredient:String?, val brief:String?, val recipe:String?, val foodImage:String?, val recipeImage:String?, val kcal:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(recipeSeq)
        parcel.writeString(recipeName)
        parcel.writeString(ingredient)
        parcel.writeString(brief)
        parcel.writeString(recipe)
        parcel.writeString(foodImage)
        parcel.writeString(recipeImage)
        parcel.writeString(kcal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeDto> {
        override fun createFromParcel(parcel: Parcel): RecipeDto {
            return RecipeDto(parcel)
        }

        override fun newArray(size: Int): Array<RecipeDto?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$recipeSeq, $recipeName, $ingredient, $brief, $recipe, $foodImage, $recipeImage, $kcal"
    }


}