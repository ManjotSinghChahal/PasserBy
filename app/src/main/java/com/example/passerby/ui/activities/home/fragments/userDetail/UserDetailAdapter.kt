package com.example.passerby.ui.activities.home.fragments.userDetail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.passerby.R
import com.example.passerby.data.model.userDetail.UserDetailModel
import com.example.passerby.ui.activities.home.fragments.FullImageview
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.IMAGE_URL
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.GlobalHelper.BASE_URL_IMAGE
import kotlinx.android.synthetic.main.user_photo_adapter.view.*



class UserDetailAdapter(
    val context: Context,
    val profileModel: UserDetailModel

) : RecyclerView.Adapter<UserDetailAdapter.ViewHolder>()
{

    //  var ondelIcon : onDeleteIcon


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.user_photo_adapter,parent,false)

        view.img_userPhotoAdapter.getLayoutParams().height = Constants.SCREEN_WIDTH_PX/3



        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileModel.body.userImages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val userData = profileModel.body.userImages.get(holder.adapterPosition)
        holder.bindItems(userData)


    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

        fun bindItems(userData: com.example.passerby.data.model.userDetail.UserImage)
        {




            Glide.with(context).load("${BASE_URL_IMAGE}${userData.image}")
                .into(itemView.img_userPhotoAdapter)




            itemView.img_userPhotoAdapter.setOnClickListener {


                val bundle = Bundle()
                bundle.putString(IMAGE_URL,"${BASE_URL_IMAGE}${userData.image}")
                val frag = FullImageview()
                frag.arguments = bundle

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ViewCompat.getTransitionName(itemView.img_userPhotoAdapter)?.let { it1 ->
                        (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.container_home, frag)
                            .addSharedElement(itemView.img_userPhotoAdapter, it1)
                            .addToBackStack(null).commit()
                    }
                } else {

                    (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.container_home, frag)
                        .addToBackStack(null).commit()
                }

            }

            itemView.rel_deleteImage.setOnClickListener {


            }





        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }





}