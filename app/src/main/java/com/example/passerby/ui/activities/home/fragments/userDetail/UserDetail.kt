package com.example.passerby.ui.activities.home.fragments.userDetail


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.example.passerby.R
import com.example.passerby.data.model.userDetail.UserDetailModel
import com.example.passerby.ui.activities.home.HomeContract
import com.example.passerby.ui.activities.home.HomePresenterImp
import com.example.passerby.ui.activities.home.fragments.chat.Chat
import com.example.passerby.ui.activities.home.fragments.map.MapFragment
import com.example.passerby.ui.activities.home.fragments.reportBlock.BlockReport
import com.example.passerby.ui.baseClasses.App
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.CHAT_ID
import com.example.passerby.utils.Constants.FRIEND_ID
import com.example.passerby.utils.Constants.FRIEND_IMAGE
import com.example.passerby.utils.Constants.FRIEND_NAME
import com.example.passerby.utils.Constants.USER_ID
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.SocketManager
import io.socket.client.Socket
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.fragment_user_detail.view.*
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import org.json.JSONArray
import org.json.JSONObject


class UserDetail : Fragment() , HomeContract.UserDetailView , SocketManager.Observer {



    var mSocket: Socket? = null
    var mSocketManager: SocketManager? = null
    lateinit var presenter: HomeContract.HomePresenter
    var profileModel: UserDetailModel? = null
    var userDetailAdpt: UserDetailAdapter? = null
    var frnd_id: String? = null
    var chat_id: String? = null
    var userDetailModel : UserDetailModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)

        initializer(view)
        clickListener(view)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    fun initializer(view: View) {
        view.img_profileImg_userDetail.getLayoutParams().height = (Constants.SCREEN_HEIGHT_PX * 0.65).toInt()

        val bundle = arguments
        if (bundle!=null && bundle.containsKey(FRIEND_ID))
        {
            frnd_id = bundle.getString(FRIEND_ID)
            chat_id = bundle.getString(CHAT_ID)

            val app = activity?.application as App
            mSocket = app.getSocket()
            mSocketManager = App.instance!!.getSocketManager()
            val jsonObject = JSONObject()
            jsonObject.put(mSocketManager!!.USER_ID, frnd_id)
            mSocketManager!!.onTotalPassed(jsonObject)

            presenter = HomePresenterImp(this)
            GlobalHelper.showProgress(activity as AppCompatActivity)
            frnd_id?.let {  presenter.userDetail(it) }



        }



    }


    fun clickListener(view: View) {
        view.recyclerview_userPics.layoutManager = GridLayoutManager(activity, 3) as RecyclerView.LayoutManager?
        view.img_back_userDetail.setOnClickListener { activity?.let { it.onBackPressed() } }


        view.img_chat_userDetail.setOnClickListener {
            activity?.let {

                if (userDetailModel!=null)
                {
                    val bundle = Bundle()
                    bundle.putString(CHAT_ID, chat_id)
                    bundle.putString(FRIEND_ID, frnd_id)
                    bundle.putString(FRIEND_NAME, userDetailModel!!.body.usersDetail.name)
                    bundle.putString(FRIEND_IMAGE, "${GlobalHelper.BASE_URL_IMAGE}${userDetailModel!!.body.usersDetail.profileImage}")
                    val frag = Chat()
                    frag.arguments = bundle


                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.container_home,frag)
                        .addToBackStack(null).commit()
                }

            }
        }
        view.lin_map_userDetail.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container_home, MapFragment())
                    .addToBackStack(null).commit()
            }

        }

        view.txt_blockReport.setOnClickListener {

           val bundle = Bundle()
            val frag = BlockReport()

           // bundle.putString(USER_ID,"")
            bundle.putString(FRIEND_ID,frnd_id)  // persons id whom you want to block
            frag.arguments = bundle

            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container_home, frag)
                    .addToBackStack(null).commit()
            }
        }
    }


    override fun onUserDetailView(userDetail: UserDetailModel) {
        GlobalHelper.hideProgress()
        userDetail.message.let { GlobalHelper.snackBar(view!!.rootUserDetail, it) }

        username_userDetail.text = userDetail.body.usersDetail.name
        bio_userDetail.text = userDetail.body.usersDetail.bio

        userDetailModel = userDetail

          activity?.let {
              Glide.with(it).load("${GlobalHelper.BASE_URL_IMAGE}${userDetail.body.usersDetail.profileImage}")
                  .into(img_profileImg_userDetail)
          }

       /* Glide.with(this)
            .asBitmap()
            .load("${GlobalHelper.BASE_URL_IMAGE}${userDetail.body.usersDetail.profileImage}")
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    img_profileImg_userDetail.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })*/




        userDetailAdpt = activity?.let {
            UserDetailAdapter(
                it, userDetail)
        }
        recyclerview_userPics?.let { it.adapter = userDetailAdpt }
    }

    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.rootUserDetail, it) }
    }

    override fun onResume() {
        super.onResume()
        mSocketManager?.onRegister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocketManager?.unRegister(this)
        mSocketManager?.disconnectTotalPassed()
    }


    override fun onChatDetail(event: String, args: JSONArray) {
    }

    override fun onChatList(event: String, args: JSONArray) {
    }

    override fun onTotalPassed(event: String, args: JSONObject) {

        try {

            val jsonObject = JSONObject(args.toString())
            val totalPassers =  jsonObject.get("users")
            activity?.let {
                it.runOnUiThread {

                    view?.passerCount_userDetail?.text = totalPassers.toString()
                }
            }



        }catch (ex : Exception)
        {}
    }

    override fun onNearbyUsersList(event: String, args: JSONArray) {
    }

    override fun onResponseArray(event: String, args: JSONArray) {
    }

    override fun onResponseObject(event: String, args: JSONObject) {
    }

}
