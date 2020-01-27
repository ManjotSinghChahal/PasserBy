package com.example.passerby.ui.activities.home.fragments.chat


import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.passerby.R
import com.example.passerby.data.model.getChatDetail.GetChatDetail
import com.example.passerby.ui.baseClasses.App
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.CHAT_ID
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.SharedPrefUtil
import com.example.passerby.utils.SocketManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


class Chat : Fragment(), SocketManager.Observer {

    var mSocket: Socket? = null
    var mSocketManager: SocketManager? = null
    var chat_id: String? = null
    var friend_id: String? = null
     lateinit var chatAdapter: ChatAdapter
    lateinit var chatListNew: ArrayList<GetChatDetail>
    // var friend_id: String? = null

    var recyclerview : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)


        initialize(view)
        clickListener(view)

      
        return view
    }

    override fun onResume() {
        super.onResume()
        mSocketManager?.onRegister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocketManager?.unRegister(this)
        mSocketManager?.disconnectChatDetail()
    }

    fun initialize(view: View) {

        recyclerview = view.findViewById(R.id.recyclerview_chat)

        val bundle = arguments
        if (bundle != null && bundle.containsKey(CHAT_ID)) {
            chat_id = bundle.getString(CHAT_ID)
            friend_id = bundle.getString(Constants.FRIEND_ID)

            Log.e("chatFrndID", friend_id)

            view.img_Chat.text = bundle.getString(Constants.FRIEND_NAME)
            activity?.let {
                Glide.with(it).load(bundle.getString(Constants.FRIEND_IMAGE))
                    .into(view.img_chat)
            }


            val app = activity?.application as App
            mSocket = app.getSocket()
            mSocketManager = App.instance!!.getSocketManager()
            val jsonObject = JSONObject()
            jsonObject.put(mSocketManager!!.CHAT_ID, chat_id)
            mSocketManager!!.onDetailChat(jsonObject)
            //  mSocketManager!!.onNewMessage()


        }

        recyclerview?.layoutManager = LinearLayoutManager(activity)

    }

    fun clickListener(view: View) {
        view.back_chat.setOnClickListener { activity?.let { it.onBackPressed() } }

        view.txt_sendChat.setOnClickListener {

            val jsonObject = JSONObject()
            jsonObject.put("user_id", SharedPrefUtil.getInstance()?.getUserId())
            jsonObject.put("user2id", friend_id)
            jsonObject.put("message_type", 1)
            jsonObject.put("sender_name", "")
            jsonObject.put("sender_profile_image", "")
            jsonObject.put("receiver_name", "")
            jsonObject.put("receiverProfileImage", "")
            jsonObject.put("message", view.edt_messageChat.text.toString().trim())

            mSocketManager!!.onSendMessage(jsonObject)


            view.edt_messageChat.setText("")

            // {"user_id":"644","user2id":"1285","message_type":1,"sender_name":"","sender_profile_image":"","receiver_name":"","receiverProfileImage":"","message":"hlo"}





        }

    }


    override fun onChatDetail(event: String, args: JSONArray) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        var chatList: List<GetChatDetail> =
            gson.fromJson(args.toString(), object : TypeToken<List<GetChatDetail>>() {}.type)

        chatListNew = ArrayList<GetChatDetail>()
        chatListNew.addAll(chatList.reversed())



        try {

          //  chatListNew.reversed()
            chatAdapter = activity?.let { ChatAdapter(it, chatListNew, friend_id) }!!


            activity?.runOnUiThread {
                activity?.let {
                    recyclerview?.adapter = chatAdapter
                }
            }
        } catch (ex: Exception) {
        }


    }

    override fun onChatList(event: String, args: JSONArray) {

    }

    override fun onTotalPassed(event: String, args: JSONObject) {

    }

    override fun onNearbyUsersList(event: String, args: JSONArray) {
    }

    override fun onResponseArray(event: String, args: JSONArray) {

        if (event.equals(mSocketManager?.SEND_MESSAGE)) {
            /* //  chatListNew
               val gson = GsonBuilder().create()
               val payloadStr = gson.toJson(args)
               val gson2 = Gson()
               val list_new = gson2.fromJson(payloadStr, GetChatDetail::class.java)
               chatListNew.get(0).user_id = list_new*/
        }

    }

    override fun onResponseObject(event: String, args: JSONObject) {

        activity?.runOnUiThread {

            if (event.equals(mSocketManager!!.NEW_MESSAGE)) {



                val jsonData = JSONObject(args.toString())
                val id = jsonData.getString("id").toInt()
                val message = jsonData.getString("message")
                val thumb = jsonData.getString("thumb")
                val type = jsonData.getString("type").toInt()
                val status = jsonData.getString("status").toInt()
                val is_seen = jsonData.getString("is_seen").toInt()
                val user_id = jsonData.getString("user_id").toInt()
                val user2id = jsonData.getString("user2id").toInt()
                val time = jsonData.getString("time").toInt()

                var data = GetChatDetail(id, is_seen, message, status, thumb, time, type, user2id, user_id)

                chatListNew.add(data)
                chatAdapter.notifyDataSetChanged()
                recyclerview?.scrollToPosition(chatListNew.size - 1)


            } else {

            }
        }
    }


}
