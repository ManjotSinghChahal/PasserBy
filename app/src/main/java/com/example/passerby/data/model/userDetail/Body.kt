package com.example.passerby.data.model.userDetail

data class Body(
    val createdAt: Long,
    val email: String,
    val id: Int,
    val location: List<Location>,
    val userImages: List<UserImage>,
    val usersDetail: UsersDetail
)