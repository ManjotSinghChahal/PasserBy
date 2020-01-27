package com.example.passerby.data.model.termsConditions

import android.os.Parcelable
import com.example.passerby.data.model.privacyPolicy.Body
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TermsConditionsModel(
    val body: Body,
    val message: String
) : Parcelable