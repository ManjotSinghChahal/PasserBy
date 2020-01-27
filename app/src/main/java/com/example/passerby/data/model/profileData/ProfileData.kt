package com.example.passerby.data.model.profileData

import android.os.Parcel
import android.os.Parcelable

class ProfileData() : Parcelable
{
    lateinit var name : String
    lateinit var password : String
    var profilePic : String = ""
    var dob : String = ""
    lateinit var email : String

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        password = parcel.readString()
        profilePic = parcel.readString()
        dob = parcel.readString()
        email = parcel.readString()
    }

    constructor(name: String, password: String, profilePic: String, dob: String, email: String) : this() {
        this.name = name
        this.password = password
        this.profilePic = profilePic
        this.dob = dob
        this.email = email
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeString(profilePic)
        parcel.writeString(dob)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileData> {
        override fun createFromParcel(parcel: Parcel): ProfileData {
            return ProfileData(parcel)
        }

        override fun newArray(size: Int): Array<ProfileData?> {
            return arrayOfNulls(size)
        }
    }
}