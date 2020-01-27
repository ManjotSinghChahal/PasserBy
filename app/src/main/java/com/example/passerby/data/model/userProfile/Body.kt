package com.example.passerby.data.model.userProfile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Body(
    val email: String,
    val id: Int,
    val userImages: List<UserImage>,
    val usersDetail: UsersDetail
) : Parcelable