package com.example.passerby.data.model.addProfile

data class Body(
    val email: String,
    val id: Int,
    val token: String,
    val userDetail: UserDetail
)