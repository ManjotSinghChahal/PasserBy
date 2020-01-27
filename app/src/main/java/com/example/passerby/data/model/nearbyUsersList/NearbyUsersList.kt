package com.example.passerby.data.model.nearbyUsersList

data class NearbyUsersList(
    val block_status: Int,
    val chat_id: Int,
    val id: Int,
    val latitude: String,
    val location: String,
    val longitude: String,
    val name: String,
    val profileImage: String,
    val status: Int,
    val time: Int
)