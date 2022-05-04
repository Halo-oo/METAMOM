package com.metamom.bbssample

import android.os.Parcel
import android.os.Parcelable

class MemberDto(
    val id: String?,
    val pwd: String?,
    val name: String?,
    val email: String?,
    val gender:String?,
    val phone: String?,
    val nickname:String?,
    val height: Double,
    val weight: Double,
    val del: String?,
    val auth: Int,
    val subscribe: Int,
    val birth: String?,
    val bmi: Double?,
    val profile: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(pwd)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(gender)
        parcel.writeString(phone)
        parcel.writeString(nickname)
        parcel.writeDouble(height)
        parcel.writeDouble(weight)
        parcel.writeString(del)
        parcel.writeInt(auth)
        parcel.writeInt(subscribe)
        parcel.writeString(birth)
        parcel.writeDouble(bmi!!)
        parcel.writeString(profile)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberDto> {
        override fun createFromParcel(parcel: Parcel): MemberDto {
            return MemberDto(parcel)
        }

        override fun newArray(size: Int): Array<MemberDto?> {
            return arrayOfNulls(size)
        }
    }

    /* #21# 확인을 위해 toString() 추가 */
    override fun toString(): String {
        return "MemberDto(id=${id}, height=${height}, weight=${weight}, subscribe=${subscribe}"
    }
}
