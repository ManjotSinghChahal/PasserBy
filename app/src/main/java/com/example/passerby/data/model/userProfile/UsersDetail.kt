package com.example.passerby.data.model.userProfile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersDetail(
    var bio: String,
    val dob: String,
    val latitude: String,
    val location: String,
    val longitude: String,
    val name: String,
    val profileImage: String
) : Parcelable