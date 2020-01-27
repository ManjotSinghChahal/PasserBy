package com.example.passerby.ui.activities.home.fragments.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passerby.R
import com.example.passerby.data.model.getChatDetail.GetChatDetail
import com.example.passerby.utils.Constants
import com.example.passerby.utils.SharedPrefUtil


class ChatAdapter(
    val context: Context,
    val list: List<GetChatDetail>,
    val friend_id: String?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder  {

        if (viewType == Constants.TYPE_USER)
        {
        return (UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_viewholder, parent, false)))
        }
        else
        {
        return (FriendViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.friend_viewholder, parent, false)))
        }

    }

    override fun getItemCount(): Int { return list.size    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        if (holder.itemViewType == Constants.TYPE_FRIEND)
        {
            val friendViewHolder = holder as FriendViewHolder
            friendViewHolder.txt_friendView.text = list.get(position).message
        }
        else
        {
            val userViewHolder = holder as UserViewHolder
            userViewHolder.txt_userView.text = list.get(position).message
        }



    }

    override fun getItemViewType(position: Int): Int {


        if (SharedPrefUtil.getInstance()?.getUserId().equals(list.get(position).user_id.toString()))
        {
            return Constants.TYPE_USER
        }
        return  Constants.TYPE_FRIEND


    }


    inner class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val txt_userView: TextView = itemView.findViewById<TextView>(R.id.txt_userView)
    }

    inner class FriendViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val txt_friendView: TextView = itemView.findViewById<TextView>(R.id.txt_friendView)
    }



}



