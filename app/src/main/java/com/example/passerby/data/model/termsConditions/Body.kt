package com.example.passerby.data.model.termsConditions

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Body(
    val description: String,
    val id: Int,
    val slug: String,
    val status: Int,
    val title: String,
    val updatedAt: String
) :Parcelable