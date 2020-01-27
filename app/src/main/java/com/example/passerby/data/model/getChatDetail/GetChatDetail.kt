package com.example.passerby.data.model.getChatDetail

data class GetChatDetail(
    val id: Int,
    val is_seen: Int,
    val message: String,
    val status: Int,
    val thumb: String,
    val time: Int,
    val type: Int,
    val user2id: Int,
    val user_id: Int
)