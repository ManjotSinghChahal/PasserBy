package com.example.passerby.ui.activities.home.fragments.userList

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.passerby.R
import com.example.passerby.data.model.nearbyUsersList.NearbyUsersList
import com.example.passerby.ui.activities.home.fragments.userDetail.UserDetail
import com.example.passerby.utils.Constants.CHAT_ID
import com.example.passerby.utils.Constants.FRIEND_ID
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.GlobalHelper.getTimeAgo
import kotlinx.android.synthetic.main.userlist_adapter.view.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class UserListAdapter(val context: Context, chatList: List<NearbyUsersList>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var nearbyUsersList: List<NearbyUsersList>? = null

    init {
        nearbyUsersList = chatList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.userlist_adapter, parent, false))
    }

    override fun getItemCount(): Int {
        return nearbyUsersList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        nearbyUsersList?.get(position)?.let { holder.bindItems(it) }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(nearbyList: NearbyUsersList) {

            itemView.username_userListAdpter.text = nearbyList.name

            Glide.with(context).load("${GlobalHelper.BASE_URL_IMAGE}${nearbyList.profileImage}")
                .into(itemView.img_userListAdapter)

           /* itemView.txt_timeUserList.text = getTimeAgo(nearbyList.time.toLong())


            val now = Timestamp(System.currentTimeMillis()).getTime()
            Log.e("gtgtg",now.toString())
            //  1562934644909   now 1
            //  1562934856322   now 1
            //  1562928465   prev 1
            //  1562936017017   prev 1

            val formatter =  SimpleDateFormat("dd/MM/yyyy");
            val dateString = formatter.format( Date(1562326154))
            Log.e("cccxxxxx",dateString)
*/





            itemView.rel_userListAdapter.setOnClickListener {




                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


                    val bundle = Bundle()
                    bundle.putString(FRIEND_ID, nearbyList.id.toString())
                    bundle.putString(CHAT_ID, nearbyList.chat_id.toString())
                    val frag = UserDetail()
                    frag.arguments = bundle

                    ViewCompat.getTransitionName(itemView.img_userListAdapter)?.let { it1 ->
                        (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.container_home, frag)
                            .addSharedElement(itemView.img_userListAdapter, it1)
                            // .addSharedElement(itemView.img_userListAdapter, itemView.img_userListAdapter.transitionName)
                            .addToBackStack(null).commit()
                    }
                } else {


                    val bundle = Bundle()
                    bundle.putString(FRIEND_ID, nearbyList.id.toString())
                    bundle.putString(CHAT_ID, nearbyList.chat_id.toString())
                    val frag = UserDetail()
                    frag.arguments = bundle

                    (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.container_home, frag)
                        .addToBackStack(null).commit()
                }
            }
        }
    }

    fun calculateTime(previousTime: Int, txt_timeUserList: TextView)
    {
        Log.e("printNearbyLisr----->>>", Timestamp(System.currentTimeMillis()).getTime().toString())


/*        val currentTimeStamp = Timestamp(System.currentTimeMillis()).getTime()
        val milliseconds = currentTimeStamp - previousTime
        var seconds = milliseconds.toInt() / 1000
        getTimeAgo(1562933384392)

        if (seconds<60)
        {
            txt_timeUserList.text = "$seconds S"
        }
        if (seconds<60)
        {
            txt_timeUserList.text = "$seconds S"
        }
        else if (seconds>=60 && seconds<3600)
        {
            val minutes =    seconds/60
            txt_timeUserList.text = "$minutes M"
        }
        else if (seconds>=3600 && seconds<43200)
        {
            val hours = seconds/3600
            txt_timeUserList.text = "$hours H"
        }
        else if (seconds>=43200 && seconds<43200)
        {
            val hours = seconds/3600
            txt_timeUserList.text = "$hours H"
        }*/


    }
}