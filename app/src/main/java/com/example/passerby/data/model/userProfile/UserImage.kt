package com.example.passerby.data.model.userProfile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserImage(
    val id: Int,
    val image: String
) : Parcelable