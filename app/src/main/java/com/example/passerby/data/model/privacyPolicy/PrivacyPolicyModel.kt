package com.example.passerby.data.model.privacyPolicy

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrivacyPolicyModel(
    val body: Body,
    val message: String
) :Parcelable