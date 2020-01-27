package com.example.passerby.data.model.getChatList


  //class GetChatList {


    data class GetChatList(
        val block: Int,
        val chat_id: Int,
        val is_seen: Int,
        val message: String,
        val message_id: Int,
        val message_type: Int,
        val name: String,
        val otheruser: Int,
        val profile_image: String,
        val sender_id: Int,
        val status: Int,
        val time: Int
    )
