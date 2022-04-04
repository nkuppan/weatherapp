package com.nkuppan.weatherapp.presentation.alert

import android.os.Parcel
import android.os.Parcelable

data class AlertUIModel(
    val title: String,
    val date: String,
    val description: String,
    val senderName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(description)
        parcel.writeString(senderName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlertUIModel> {
        override fun createFromParcel(parcel: Parcel): AlertUIModel {
            return AlertUIModel(parcel)
        }

        override fun newArray(size: Int): Array<AlertUIModel?> {
            return arrayOfNulls(size)
        }
    }
}