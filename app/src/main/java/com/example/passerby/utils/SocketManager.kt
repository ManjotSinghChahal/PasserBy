package com.example.passerby.utils

import android.app.Activity
import android.util.Log
import com.example.passerby.data.model.getChatDetail.GetChatDetail
import com.example.passerby.data.model.getChatList.GetChatList
import com.example.passerby.ui.activities.home.fragments.userList.UserChatListAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.URISyntaxException
import java.util.*

class SocketManager {

    // sockets events

    val EVENT_CHAT_LIST = "chat_list"
    val EVENT_SET_LANGUAGE = "set_language"
    val EVENT_GET_LANGUAGE = "get_language"
    val SOCKET_CONNECT = "socket_connect"
    val CONNECT_USER = "connect_user"
    val USER_DISCONNECT = "user_disconnect"
    val CONNECT_LISTENER = "connect_listener"
    val ERROR_MESSAGE = "error_message"
    val LISTENER_CHAT_LIST = "chat_list"
    val CHAT_LIST = "chat_list"
    val LISTENER_CHAT_DETAIL = "chat"
    val LISTENER_SEND_MESSAGE = "new_message"
    val SEND_MESSAGE = "send_message"
    val GET_CHAT_DETAIL = "get_chat"
    val NEW_MESSAGE = "new_message"
    val LISTENER_NEW_MESSAGE = "send_message"
    val TOTAL_PASSED = "totalPassed"
    val BLOCK_USER = "block_user"
    val NEARBY_USERS_LIST = "nearByUsersList"
    val LISTENER_NEARBY_USERS = "users"
    val LISTENER_NEAR_BY_USERS = "nearByUser"
    val UPDATE_LOCATION = "update_location"



    val CHAT_ID = "chat_id"
    val USER_ID = "user_id"
    val REASON = "reason"
    val ACTION = "action"


    private var mSocket: Socket? = null
    private val mActivity: Activity? = null
    private var observerList: MutableList<Observer>? = null
    // private var observerBlockUser: MutableList<ObserveBlock>? = null

    private var activeUser = 0
    private val filePath = ""
    private var forDeliveryOnly = false


    private val listener = Emitter.Listener { }

    private val onDisconnect = Emitter.Listener { Log.i("Socket", "DISCONNECTED") }
    private val onConnectError = Emitter.Listener {
        Log.i("Socket", "CONNECTION ERROR")

    }
    private val onUserDisconnect = Emitter.Listener { Log.i("Socket", "ON_USERDUSCONNECT") }
    private val onErrorMessage = Emitter.Listener {
        Log.i("Socket", "CONNECTION ERROR MESSAGE")
        for (observer in observerList!!) {
            //            observer.onError(CONNECT_USER, args);
        }

    }


    private val onConnect = Emitter.Listener { args ->
        Log.i("Socket", "CONNECTED")
        if (isConnected()) {
            try {
                Log.i("Socket", "want to go online")
                val jsonObject = JSONObject()
                jsonObject.put("user_id", SharedPrefUtil.getInstance()!!.getUserId())
                mSocket!!.off(CONNECT_LISTENER)
                mSocket!!.on(CONNECT_LISTENER, onConnectListener)
                //                eventUserDisconnect();
                mSocket!!.emit(CONNECT_USER, jsonObject)
                //                    if (activeUser != 0) {
                //                        eventCheckOnlineStatus(activeUser, Integer.parseInt(SharedPrefUtil.getInstance().getUserId()));
                //                    }
                connectChatSocket()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        } else {
            initializeSocket()
        }
    }

    fun onTotalPassed(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(TOTAL_PASSED, onTotalPassedFetch)
                mSocket!!.on(TOTAL_PASSED, onTotalPassedFetch)
                mSocket!!.emit(TOTAL_PASSED, jsonObject)
            }
            else
            {
                mSocket!!.off(TOTAL_PASSED, onTotalPassedFetch)
                mSocket!!.on(TOTAL_PASSED, onTotalPassedFetch)
                mSocket!!.emit(TOTAL_PASSED, jsonObject)
            }
            Log.i("Socket", "chat_detail_fetched")
        }
    }

    fun onNearByUser(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(LISTENER_NEARBY_USERS, onNearByUsers)
                mSocket!!.on(LISTENER_NEARBY_USERS, onNearByUsers)
                mSocket!!.emit(NEARBY_USERS_LIST, jsonObject)
            }
            else
            {
                mSocket!!.off(LISTENER_NEARBY_USERS, onNearByUsers)
                mSocket!!.on(LISTENER_NEARBY_USERS, onNearByUsers)
                mSocket!!.emit(NEARBY_USERS_LIST, jsonObject)
            }
            Log.i("Socket", "chat_detail_fetched")
        }
    }
    fun onBlockUser(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(BLOCK_USER, onBlockUsers)
                mSocket!!.on(BLOCK_USER, onBlockUsers)
                mSocket!!.emit(BLOCK_USER, jsonObject)
            }
            else
            {
                mSocket!!.off(BLOCK_USER, onBlockUsers)
                mSocket!!.on(BLOCK_USER, onBlockUsers)
                mSocket!!.emit(BLOCK_USER, jsonObject)
            }
            Log.i("Socket", "chat_detail_fetched")
        }
    }

    fun onDetailChat(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(LISTENER_CHAT_DETAIL, onChatDetailFetch)
                mSocket!!.on(LISTENER_CHAT_DETAIL, onChatDetailFetch)
                mSocket!!.emit(GET_CHAT_DETAIL, jsonObject)
            }
            else
            {
                mSocket!!.off(LISTENER_CHAT_DETAIL, onChatDetailFetch)
                mSocket!!.on(LISTENER_CHAT_DETAIL, onChatDetailFetch)
                mSocket!!.emit(GET_CHAT_DETAIL, jsonObject)
            }
            Log.i("Socket", "chat_detail_fetched")
        }
    }

    fun onSendMessage(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
              //  mSocket!!.off(LISTENER_SEND_MESSAGE, onNewMessage)
              //  mSocket!!.on(LISTENER_SEND_MESSAGE, onNewMessage)
                mSocket!!.emit(SEND_MESSAGE, jsonObject)
            }
            else
            {
              //  mSocket!!.off(LISTENER_SEND_MESSAGE, onNewMessage)
             //   mSocket!!.on(LISTENER_SEND_MESSAGE, onNewMessage)
                mSocket!!.emit(SEND_MESSAGE, jsonObject)
            }
            Log.i("Socket", "MESSAGE_SENT")
        }
    }

/*    fun onNewMessage() {
       // if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(LISTENER_SEND_MESSAGE, onMessageSent)
                mSocket!!.on(LISTENER_SEND_MESSAGE, onMessageSent)
               // mSocket!!.emit(NEW_MESSAGE, onNewMessageReceived)
            }
            else
            {
                mSocket!!.off(LISTENER_SEND_MESSAGE, onMessageSent)
                mSocket!!.on(LISTENER_SEND_MESSAGE, onMessageSent)
              //  mSocket!!.emit(NEW_MESSAGE, jsonObject)
            }
            Log.i("Socket", "chat_detail_fetched")
     //   }
    }*/

    fun onLocationUpdate(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(LISTENER_NEAR_BY_USERS, onLocationUpdate)
                mSocket!!.on(LISTENER_NEAR_BY_USERS, onLocationUpdate)
                mSocket!!.emit(UPDATE_LOCATION, jsonObject)
            }
            else
            {
                mSocket!!.off(LISTENER_NEAR_BY_USERS, onLocationUpdate)
                mSocket!!.on(LISTENER_NEAR_BY_USERS, onLocationUpdate)
                mSocket!!.emit(UPDATE_LOCATION, jsonObject)
            }
            Log.i("Socket", "chat_detail_fetched")
        }
    }



     fun onChatList(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(LISTENER_CHAT_LIST, onChatFetch)
                mSocket!!.on(LISTENER_CHAT_LIST, onChatFetch)
                mSocket!!.emit(CHAT_LIST, jsonObject)
            }
            else{
                mSocket!!.off(LISTENER_CHAT_LIST, onChatFetch)
                mSocket!!.on(LISTENER_CHAT_LIST, onChatFetch)
                mSocket!!.emit(CHAT_LIST, jsonObject)
            }
            Log.i("Socket", "chat_list_fetched")
        }
    }


    private val onChatFetch = Emitter.Listener { args ->
        Log.e("Socket", "ONNEWMESSAGWE")
        try {
            val data = args[0] as JSONArray
            // val data = args[0] as JSONObject


            Log.e("Socket", data.toString())
         //   val gson = GsonBuilder().setPrettyPrinting().create()
         //   var chatList: List<GetChatList> = gson.fromJson(data.toString(), object : TypeToken<List<GetChatList>>() {}.type)
            // val jsonPersonList: String = gson.toJson(personList)

            for (observer in observerList!!) {
                observer.onChatList(GET_CHAT_DETAIL, data)
            }

        } catch (ex: Exception) {
            Log.e("printResult",ex.message)
            Log.e("printResult",ex.localizedMessage)
            ex.localizedMessage

        }
    }

    private val onChatDetailFetch = Emitter.Listener { args ->


        try {
            val data = args[0] as JSONArray
         //   Log.e("SocketData", data.toString())
          //  val gson = GsonBuilder().setPrettyPrinting().create()
         //   var chatList: List<GetChatDetail> = gson.fromJson(data.toString(), object : TypeToken<List<GetChatDetail>>() {}.type)

            for (observer in observerList!!) {
                observer.onChatDetail(GET_CHAT_DETAIL, data)
            }

        } catch (ex: Exception) {
            Log.e("printResult",ex.message)
            Log.e("printResult",ex.localizedMessage)

        }
    }

    private val onMessageSent = Emitter.Listener { args ->


        try {
            val data = args[0] as JSONObject
           Log.e("onMessageSent",data.toString())

            for (observer in observerList!!) {
                observer.onResponseObject(SEND_MESSAGE, data)
            }


        } catch (ex: Exception) {
            Log.e("onMessageSent",ex.message)
        }
    }

    private val onNewMessageReceived = Emitter.Listener { args ->


        try {
            val data = args[0] as JSONObject
             Log.e("onMessageReceived----",data.toString())

            for (observer in observerList!!) {
                observer.onResponseObject(SEND_MESSAGE, data)
            }


        } catch (ex: Exception) {
              Log.e("onMessageReceived----",ex.message)
        }
    }

    private val onLocationUpdate = Emitter.Listener { args ->


        try {
            val data = args[0] as String
           // Log.e("printResultMsgSent",data.toString())


        } catch (ex: Exception) {
          //  Log.e("printResultMsgSent",ex.message)
        }
    }

    private val onTotalPassedFetch = Emitter.Listener { args ->


        try {
            val data = args[0] as JSONObject

            for (observer in observerList!!) {
                observer.onTotalPassed(TOTAL_PASSED, data)
            }

        } catch (ex: Exception) { }
    }

    private val onNearByUsers = Emitter.Listener { args ->


        try {
            val data = args[0] as JSONArray

            for (observer in observerList!!) {
                observer.onNearbyUsersList(TOTAL_PASSED, data)
            }

        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onBlockUsers = Emitter.Listener { args ->


        try {
            val data = args[0] as JSONArray


            for (observer in observerList!!) {
                observer.onResponseArray(BLOCK_USER, data)
            }

        } catch (ex: Exception) {

            ex.localizedMessage
        }
    }


    private val onConnectListener = Emitter.Listener { args ->
        try {
            //            JSONObject data = (JSONObject) args[0];
            Log.i("Socket", "onConnectionListener : I am online now." /*+ data.getString("error_message")*/)
            //                SharedPrefUtil.getInstance().saveSocketId(data.getString("socket_id"));
        } catch (ex: Exception) { }
    }




    fun init() {

        initializeSocket()
    }

    fun init(forDelivery: Boolean) {
        //        chatDatabaseHelper = new ChatDatabaseHelper(App.getInstance());
        //        mediaList = new HashMap<>();
        this.forDeliveryOnly = forDelivery
        initializeSocket()
    }

    private fun initializeSocket() {
        if (mSocket == null) {
            mSocket = getSocket()
        }
        if (observerList == null || observerList!!.size == 0) {
            observerList = ArrayList()
        }
        disconnect()
        mSocket!!.on(Socket.EVENT_CONNECT, onConnect)
        //        mSocket.on(Socket.EVENT_PONG, onPongListener);
        //        mSocket.on(Socket.EVENT_PING, onPingListener);
        mSocket!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        //        mSocket.on(USER_DISCONNECT, onUserDisconnect);
        mSocket!!.on(ERROR_MESSAGE, onErrorMessage)
        //        mSocket.on(SOCKET_CONNECT, onSocketConnect);
        mSocket!!.connect()

    }

    fun getmSocket(): Socket? {
        return mSocket
    }

    fun isConnected(): Boolean {
        //        mSocket.
        return mSocket != null && mSocket!!.connected()
    }

    fun setActiveUser(activeUser: Int) {
        this.activeUser = activeUser
    }

    fun connectChatSocket() {

        if (mSocket!!.connected()) {
        mSocket!!.off(NEW_MESSAGE, onNewMessage)
//            //            mSocket.off(MESSAGE_DELIVERED, onMessageDelivered);
//            //            mSocket.off(LISTENER_READ, onMessageRead);
//            //            mSocket.off(LISTENER_ONLINE_STATUS, onlineStatus);
        mSocket!!.on(NEW_MESSAGE, onNewMessage)
//            //            mSocket.on(MESSAGE_DELIVERED, onMessageDelivered);
//            //            mSocket.on(LISTENER_ONLINE_STATUS, onlineStatus);
//            //            mSocket.on(LISTENER_READ, onMessageRead);
        } else {
//            mSocket!!.connect()
        mSocket!!.off(NEW_MESSAGE, onNewMessage)
//            //            mSocket.off(MESSAGE_DELIVERED, onMessageDelivered);
//            //            mSocket.off(LISTENER_READ, onMessageRead);
//            //            mSocket.off(LISTENER_ONLINE_STATUS, onlineStatus);
        mSocket!!.on(NEW_MESSAGE, onNewMessage)
//            //            mSocket.on(MESSAGE_DELIVERED, onMessageDelivered);
//            //            mSocket.on(LISTENER_ONLINE_STATUS, onlineStatus);
//            //            mSocket.on(LISTENER_READ, onMessageRead);
        }
    }

    fun disconnectAll() {

        if (mSocket != null) {
            //            eventUserDisconnect();
            mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
            mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket!!.off(USER_DISCONNECT, onUserDisconnect)
//            mSocket!!.off(NEW_MESSAGE, onNewMessage)
            //            mSocket.off(SOCKET_CONNECT, onSocketConnect);
            mSocket!!.off()
            mSocket!!.disconnect()

        }
    }
    fun disconnectTotalPassed() {
        if (mSocket != null) {
            mSocket!!.off(LISTENER_CHAT_LIST, onChatFetch)
        }
    }
    fun disconnectChatDetail() {
        if (mSocket != null) {
            mSocket!!.off(LISTENER_CHAT_DETAIL, onChatDetailFetch)
        }
    }

    fun disconnectBlockUser() {
        if (mSocket != null) {
            mSocket!!.off(BLOCK_USER, onBlockUsers)
        }
    }

    private fun disconnect() {
        if (mSocket != null) {
            //            eventUserDisconnect();
            mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
            mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket!!.off(USER_DISCONNECT, onUserDisconnect)
            //            mSocket.off(NEW_MESSAGE, onNewMessage);
            //            mSocket.off(SOCKET_CONNECT, onSocketConnect);
            mSocket!!.off()
            mSocket!!.disconnect()
        }
        //        mSocket.off(NEW_MESSAGE, onNewMessage);
        //        mSocket.off(SOCKET_CONNECT, onSocketConnect);
        //        mSocket.off();
    }

    fun getSocket(): Socket? {
        run {
            try {
                mSocket = IO.socket(Constants.CHAT_SERVER_URL)
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }
        }
        return mSocket
    }

    fun onRegister(observer: Observer) {
        if (observerList != null && !observerList!!.contains(observer)) {
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            observerList!!.add(observer)
        }
    }

    fun unRegister(observer: Observer) {
        if (observerList != null) {
            for (i in observerList!!.indices) {
                val model = observerList!![i]
                if (model === observer) {
                    observerList!!.remove(model)
                }
            }
        }
    }

   /* fun onRegisterBlock(observer: ObserveBlock) {
        if (observerList != null && !observerList!!.contains(observer)) {
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            observerList!!.add(observer)
        }
    }

    fun unRegisterBlock(observer: Observer) {
        if (observerList != null) {
            for (i in observerList!!.indices) {
                val model = observerList!![i]
                if (model === observer) {
                    observerList!!.remove(model)
                }
            }
        }
    }
*/

    private val onNewMessage = Emitter.Listener { args ->
        Log.e("Socket", "ONNEWMESSAGERECEIVED")
        try {
            val data = args[0] as JSONObject
            Log.e("getNewMessageHere",data.toString())

             for (observer in observerList!!) {
                observer.onResponseObject(NEW_MESSAGE, data)
            }

        } catch (ex: Exception) {
            Log.e("valuePrintHere",ex.message)
            ex.localizedMessage
        }
    }









    private fun convertFileToBytes(filePath: String): ByteArray {
        val file = File(filePath)
        val size = file.length().toInt()
        val chunks = ByteArray(size)
        Log.i("Chunks", " : " + file.length() + "  :  " + file.length() / 1000)
        try {
            val buf = BufferedInputStream(FileInputStream(file))
            buf.read(chunks, 0, chunks.size)
            buf.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return chunks
    }

    protected fun getExtension(selectedImage: String): String {
        return selectedImage.substring(selectedImage.lastIndexOf(".") + 1)
    }



   /* fun sendMessage(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(NEW_MESSAGE, onNewMessage)
                mSocket!!.on(NEW_MESSAGE, onNewMessage)
                mSocket!!.emit(SEND_MESSAGE, jsonObject)
            } else {
             //   mSocket!!.off(NEW_MESSAGE, onNewMessage)
             //   mSocket!!.on(NEW_MESSAGE, onNewMessage)
                mSocket!!.emit(SEND_MESSAGE, jsonObject)
            }
            Log.i("Socket", "New Message sent")
        }
    }*/

    /*fun chatDetail(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(LISTENER_CHAT_DETAIL, onNewMessage)
                mSocket!!.on(LISTENER_CHAT_DETAIL, onNewMessage)
                mSocket!!.emit(LISTENER_CHAT_DETAIL, jsonObject)
            } *//*else {
                mSocket!!.off(NEW_MESSAGE, onNewMessage)
                mSocket!!.on(NEW_MESSAGE, onNewMessage)
                mSocket!!.emit(SEND_MESSAGE, jsonObject)
            }*//*
            Log.i("Socket", "chat_list_fetched")
        }
    }*/


    interface Observer {


        fun onChatDetail(event: String, args: JSONArray)
        fun onChatList(event: String, args: JSONArray)
        fun onTotalPassed(event: String, args: JSONObject)
        fun onNearbyUsersList(event: String, args: JSONArray)
        fun onResponseArray(event : String, args : JSONArray)
        fun onResponseObject(event : String, args : JSONObject)
    }

/*    interface ObserveBlock
    {
        fun onBlockUser(event: String, args: JSONArray)
    }*/

}