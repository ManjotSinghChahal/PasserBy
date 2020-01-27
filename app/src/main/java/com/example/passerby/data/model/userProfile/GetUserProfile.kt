package com.example.passerby.data.model.userProfile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GetUserProfile(
    val body: Body,
    val message: String
) : Parcelable