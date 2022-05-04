package com.metamom.bbssample.subscribe

import android.os.Parcel
import android.os.Parcelable

/* #21# [구독] Dto */
class SubscribeDto(val subId: String?, val subType: Int, val subPeriod: Int, val subMorning: Int, val subLunch: Int, val subDinner: Int, val subSnack: Int, val subStartday: String?, val subEndday: String?) :Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subId)
        parcel.writeInt(subType)
        parcel.writeInt(subPeriod)
        parcel.writeInt(subMorning)
        parcel.writeInt(subLunch)
        parcel.writeInt(subDinner)
        parcel.writeInt(subSnack)
        parcel.writeString(subStartday)
        parcel.writeString(subEndday)
    }

    companion object CREATOR : Parcelable.Creator<SubscribeDto> {
        override fun createFromParcel(parcel: Parcel): SubscribeDto {
            return SubscribeDto(parcel)
        }

        override fun newArray(size: Int): Array<SubscribeDto?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "SubscribeDto(id=${subId}, type=${subType}, period=${subPeriod}, morning=${subMorning}, lunch=${subLunch}, dinner=${subDinner}, snack=${subSnack}, startday=${subStartday}, endday=${subEndday}"
    }
}