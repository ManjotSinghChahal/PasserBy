package com.example.passerby.ui.activities.home.fragments.userList

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.passerby.R
import com.example.passerby.data.model.getChatList.GetChatList
import com.example.passerby.ui.activities.home.fragments.chat.Chat
import com.example.passerby.utils.Constants.CHAT_ID
import com.example.passerby.utils.Constants.FRIEND_ID
import com.example.passerby.utils.Constants.FRIEND_IMAGE
import com.example.passerby.utils.Constants.FRIEND_NAME
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.GlobalHelper.getTimeAgo
import com.example.passerby.utils.SharedPrefUtil
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import kotlinx.android.synthetic.main.userchatlist_adapter.view.*


class UserChatListAdapter(val context: Context, val chatList: List<GetChatList>) :
    RecyclerView.Adapter<UserChatListAdapter.ViewHolder>() {
    var chatListNew: List<GetChatList>? = null

    init {
        chatListNew = chatList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.userchatlist_adapter, parent, false))
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        chatListNew?.let {
            holder.bindItems(position, it.get(position))
        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(pos: Int, getChatList: GetChatList) {

            itemView.username_userChatListAdpter.text = getChatList.name

            Glide.with(context).load("${GlobalHelper.BASE_URL_IMAGE}${getChatList.profile_image}")
                .into(itemView.img_userChatListAdapter)


            Log.e("ffffrrrr",getChatList.sender_id.toString())


            itemView.txt_time_userChatListAdpt.text = getTimeAgo(getChatList.time.toLong())





            //---------------if last msg send by user--------------------------
            if (getChatList.sender_id.toString().equals(SharedPrefUtil.getInstance()?.getUserId()))
            {
                itemView.txt_messageStatus_userChatListAdpt.text = context.getString(R.string.sent)
                itemView.chatIcon_userChatListAdpt.setImageResource(R.drawable.msg_send_read)
                itemView.txt_messageStatus_userChatListAdpt.setTextColor(context.resources.getColor(R.color.light_gray))
            }
            else
            {

                itemView.txt_messageStatus_userChatListAdpt.text = context.getString(R.string.new_message)

                //-------message not seen------
               if (getChatList.is_seen.toString().equals("0"))
               {
                   itemView.chatIcon_userChatListAdpt.setImageResource(R.drawable.unread_msg)
                   itemView.txt_messageStatus_userChatListAdpt.setTextColor(context.resources.getColor(R.color.colorBlack))
                 //  itemView.chatIcon_userChatListAdpt.setColorFilter(context.getResources().getColor(R.color.colorBlack), android.graphics.PorterDuff.Mode.MULTIPLY)
               }
                //-----message is seen--------------
                else
               {
                   itemView.chatIcon_userChatListAdpt.setImageResource(R.drawable.msg_send_read)
                   itemView.txt_messageStatus_userChatListAdpt.setTextColor(context.resources.getColor(R.color.light_gray))
               }
            }


            /*Glide.with(context)
                .asBitmap()
                .load("${GlobalHelper.BASE_URL_IMAGE}${getChatList.profile_image}")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        itemView.img_userChatListAdapter.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })*/





            itemView.rel_userListChatAdapter.setOnClickListener {

              //  Log.e("valurPPP",getChatList.sender_id.toString())
                Log.e("valurPPP",chatListNew?.get(adapterPosition)?.sender_id.toString())





                val bundle = Bundle()
                bundle.putString(CHAT_ID, getChatList.chat_id.toString())
                bundle.putString(FRIEND_ID, getChatList.otheruser.toString())
                bundle.putString(FRIEND_NAME, getChatList.name)
                bundle.putString(FRIEND_IMAGE, "${GlobalHelper.BASE_URL_IMAGE}${getChatList.profile_image}")
                val frag = Chat()
                frag.arguments = bundle

                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.container_home, frag).addToBackStack(null).commit()
            }


        }
    }
}

