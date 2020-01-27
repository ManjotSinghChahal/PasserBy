package com.example.passerby.ui.activities.home.fragments.userList


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.passerby.R
import com.example.passerby.ui.activities.home.HomeContract
import com.example.passerby.ui.activities.home.HomePresenterImp
import com.example.passerby.ui.activities.home.fragments.map.MapFragment
import com.example.passerby.ui.activities.home.fragments.setting.Setting
import com.example.passerby.ui.activities.home.fragments.userProfile.UserProfile
import com.example.passerby.utils.Constants.USERPROFILE_DATA
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.passerby.data.model.userProfile.GetUserProfile
import com.example.passerby.ui.baseClasses.App
import com.example.passerby.utils.Constants.HOME
import io.socket.client.Socket
import org.json.JSONObject
import org.json.JSONArray

import com.example.passerby.data.model.getChatList.GetChatList
import com.example.passerby.data.model.nearbyUsersList.NearbyUsersList
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.sql.Timestamp
import android.text.format.DateUtils
import com.example.passerby.utils.*


class UserList : Fragment() , HomeContract.HomeView , SocketManager.Observer{



    lateinit var presenter: HomeContract.HomePresenter

    var profileModel : GetUserProfile? = null
    var mSocket : Socket? = null
    var mSocketManager : SocketManager? = null

    companion object
    {
        var userListStatus : Boolean = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)


        initialize(view)
        clickListener(view)
        getUserProfile()

        if (userListStatus)
        {
         showUserList(view)
        }
        else
        {
          showChatList(view)
        }


        return view
    }


    fun initialize(view : View)
    {

        val app = activity?.application as App
        mSocket = app.getSocket()
        mSocketManager = App.instance!!.getSocketManager()
        val jsonObject = JSONObject()


        jsonObject.put("user_id",SharedPrefUtil.getInstance()?.getUserId())
        mSocketManager!!.onChatList(jsonObject)
        mSocketManager!!.onNearByUser(jsonObject)


        view.recyclerview_passerby.layoutManager = LinearLayoutManager(activity)
        view.recyclerview_chatHome.layoutManager = LinearLayoutManager(activity)

    }


    fun clickListener(view : View) {


        view.rel_toggleWave.setOnClickListener {
            showUserList(view)
        }


        view.rel_toggleChat.setOnClickListener {
            showChatList(view)
        }




        view.rel_map.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelable(Constants.PROFILE,profileModel)
            val frag = MapFragment()
            frag.arguments = bundle
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.container_home, frag).addToBackStack(null).commit()
        }

        view.rel_menu_userList.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelable(USERPROFILE_DATA, profileModel)
            val frag = Setting()
            frag.arguments = bundle

            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_home, frag)
                .addToBackStack(null).commit()
        }

        view.img_userProfileHome.setOnClickListener {
            val bundle = Bundle()

            if (profileModel!=null)
            bundle.putParcelable(USERPROFILE_DATA, profileModel)
            val frag = UserProfile()
            frag.arguments = bundle


            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_home, frag)
                .addToBackStack(null).commit()

        }


        view.edit_searchHome.setOnClickListener {

            if (!view.edit_searchHome.isCursorVisible)
                view.edit_searchHome.isCursorVisible = true
        }

        view.edit_searchHome.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    view.edit_searchHome.isCursorVisible = false
                    GlobalHelper.hideKeyboard(view,activity as AppCompatActivity)

                    return true
                }


                return false
            }
        })

        //-----------------to perform backpress action on keyboard open-------------
        view.edit_searchHome.setKeyImeChangeListener(object : ImeHandleEditText.KeyImeChange {
            override fun onKeyIme(keyCode: Int, event: KeyEvent) {
                if (KeyEvent.KEYCODE_BACK == event.keyCode) {
                    view.edit_searchHome.isCursorVisible = false
                }
            }
        })



    }

    fun showUserList(view : View)
    {
        view.txt_homeTitle.text = getString(R.string.passerby)
        view.recyclerview_passerby.visibility = View.VISIBLE
        view.recyclerview_chatHome.visibility = View.GONE
        view.rel_map.visibility = View.VISIBLE
        view.imgview_toggleHome.setBackgroundResource(R.drawable.wave_theme)
        userListStatus = true
    }

    fun showChatList(view : View)
    {
        view.txt_homeTitle.text = getString(R.string.chats)
        view.recyclerview_chatHome.visibility = View.VISIBLE
        view.recyclerview_passerby.visibility = View.GONE
        view.rel_map.visibility = View.GONE
        view.imgview_toggleHome.setBackgroundResource(R.drawable.chat_theme_home)
        userListStatus = false
    }

    fun getUserProfile()
    {
        presenter = HomePresenterImp(this)
        presenter.userProfile(HOME)

    }


    override fun onUserProfileViewSucces(userProfile: GetUserProfile?) {
       if (userProfile!=null)
        profileModel = userProfile!!
    }

    override fun onFailure(error: String) {

    }


    override fun onResume() {
        super.onResume()
        mSocketManager?.onRegister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocketManager?.unRegister(this)
    }



    override fun onChatDetail(event: String, args: JSONArray) {

    }

    override fun onChatList(event: String, args: JSONArray) {


        try {


            val gson = GsonBuilder().setPrettyPrinting().create()
            var chatList: List<GetChatList> = gson.fromJson(args.toString(), object : TypeToken<List<GetChatList>>() {}.type)

            Log.e("printChatList",args.toString())
            activity?.runOnUiThread {
                activity?.let {
                    view?.recyclerview_chatHome?.adapter = UserChatListAdapter(it,chatList) }
            }

        }catch (ex : Exception) { }



    }

    override fun onTotalPassed(event: String, args: JSONObject) {
    }

    override fun onNearbyUsersList(event: String, args: JSONArray) {

        try {


            val gson = GsonBuilder().setPrettyPrinting().create()
            var chatList: List<NearbyUsersList> = gson.fromJson(args.toString(), object : TypeToken<List<NearbyUsersList>>() {}.type)


            activity?.runOnUiThread {
                activity?.let {
                    view?.recyclerview_passerby?.adapter = UserListAdapter(it,chatList)
                }
            }

        }catch (ex : Exception) { }
    }

    override fun onResponseArray(event: String, args: JSONArray) {
    }

    override fun onResponseObject(event: String, args: JSONObject) {
    }




}
