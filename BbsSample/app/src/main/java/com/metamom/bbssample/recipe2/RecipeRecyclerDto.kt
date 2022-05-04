package com.metamom.bbssample.recipe2

import android.os.Parcel
import android.os.Parcelable

class RecipeRecyclerDto(val recipe:String?, val recipeImage:String?):  Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(recipe)
        parcel.writeString(recipeImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeRecyclerDto> {
        override fun createFromParcel(parcel: Parcel): RecipeRecyclerDto {
            return RecipeRecyclerDto(parcel)
        }

        override fun newArray(size: Int): Array<RecipeRecyclerDto?> {
            return arrayOfNulls(size)
        }
    }
    override fun toString(): String {
        return " $recipe, $recipeImage"
    }

}