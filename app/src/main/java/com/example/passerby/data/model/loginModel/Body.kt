package com.example.passerby.data.model.loginModel

data class Body(
    val email: String,
    val id: Int,
    val token: String,
    val userDetail: UserDetail
)