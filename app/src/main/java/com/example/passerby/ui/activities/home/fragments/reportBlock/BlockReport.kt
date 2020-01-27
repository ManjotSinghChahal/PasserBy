package com.example.passerby.ui.activities.home.fragments.reportBlock


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.ui.activities.home.fragments.blocked.Blocked
import com.example.passerby.ui.activities.home.fragments.userDetail.UserDetail
import com.example.passerby.ui.baseClasses.App
import com.example.passerby.utils.Constants
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.SharedPrefUtil
import com.example.passerby.utils.SocketManager
import io.socket.client.Socket
import kotlinx.android.synthetic.main.fragment_block_report.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


class BlockReport : Fragment(), SocketManager.Observer {


    var mSocket: Socket? = null
    var mSocketManager: SocketManager? = null
    var friend_id: String? = null
    var user_id: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_block_report, container, false)


        initialize(view)
        clickListener(view)

        return view
    }

    fun initialize(view: View) {

        user_id = SharedPrefUtil.getInstance()?.getUserId()
        val bundle = arguments
        if (bundle != null && bundle.containsKey(Constants.FRIEND_ID)) {
            friend_id = bundle.getString(Constants.FRIEND_ID)
        }

        val app = activity?.application as App
        mSocket = app.getSocket()
        mSocketManager = App.instance!!.getSocketManager()

    }

    fun clickListener(view: View) {


        view.back_blockReport.setOnClickListener {
           activity?.let { it.onBackPressed() }
            // moveToFrag()


        }

        view.txt_reason1.setOnClickListener {
            block(view.txt_reason1.text.toString().trim())
        }

        view.txt_reason2.setOnClickListener {
            block(view.txt_reason2.text.toString().trim())
        }

        view.txt_reason3.setOnClickListener {
            block(view.txt_reason3.text.toString().trim())
        }

        view.txt_reason4.setOnClickListener {
            block(view.txt_reason4.text.toString().trim())
        }

        view.txt_reason5.setOnClickListener {
            block(view.txt_reason5.text.toString().trim())
        }
    }


    fun block(message: String) {
        val jsonObject = JSONObject()
        jsonObject.put("user_id", user_id)
        jsonObject.put("user2id", friend_id)
        jsonObject.put(mSocketManager!!.ACTION, "block")
        jsonObject.put(mSocketManager!!.REASON, message)
        mSocketManager!!.onBlockUser(jsonObject)
    }


    override fun onResume() {
        super.onResume()
        mSocketManager?.onRegister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocketManager?.unRegister(this)
        mSocketManager?.disconnectBlockUser()
    }


    override fun onChatDetail(event: String, args: JSONArray) {
    }

    override fun onChatList(event: String, args: JSONArray) {
    }

    override fun onTotalPassed(event: String, args: JSONObject) {
    }

    override fun onNearbyUsersList(event: String, args: JSONArray) {
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onResponseArray(event: String, args: JSONArray) {

        try {

            if (event.equals(mSocketManager?.BLOCK_USER)) {
                val obj: JSONObject = args.getJSONObject(0)
                val msg: String = obj.get("msg").toString()
                GlobalHelper.snackBar(view!!.rootBlock, msg)
                moveToFrag()
            }


        } catch (ex: Exception) {
        }

    }

    override fun onResponseObject(event: String, args: JSONObject) {


    }

    fun moveToFrag()
    {

       /* activity?.let { it.onBackPressed() }
        val bundle = Bundle()
        bundle.putString(Constants.FRIEND_ID, friend_id)
        val frag = UserDetail()
        frag.arguments = bundle*/

        activity?.let { it.supportFragmentManager.beginTransaction()
            .replace(R.id.container_home, Blocked())
            .addToBackStack(null).commit() }



    }

}
