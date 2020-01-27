package com.example.passerby.ui.activities.home.fragments.userProfile

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
import com.example.passerby.data.model.userProfile.GetUserProfile
import com.example.passerby.data.model.userProfile.UserImage
import com.example.passerby.ui.activities.home.fragments.FullImageview
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.IMAGE_URL
import com.example.passerby.utils.Constants.USER_DETAIL
import com.example.passerby.utils.Constants.USER_Profile
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.GlobalHelper.BASE_URL_IMAGE
import kotlinx.android.synthetic.main.user_photo_adapter.view.*
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import android.os.Environment
import com.bumptech.glide.load.engine.DiskCacheStrategy


class UserPhotoAdapter(
    val context: Context,
    val type: String,
    val profileModel: GetUserProfile,
    val callback: onDeleteIcon,
    val boolVal : Boolean
) : RecyclerView.Adapter<UserPhotoAdapter.ViewHolder>()
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

        fun bindItems(userData: UserImage)
        {


            if (type.equals(USER_DETAIL))
                itemView.img_del_userAdapter.visibility = View.GONE
            else if (type.equals(USER_Profile))
                itemView.img_del_userAdapter.visibility = View.VISIBLE



                Glide.with(context).load("$BASE_URL_IMAGE${userData.image}")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                callback.onDeleteSuccess(userData.id.toString())

            }





        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }



    public interface onDeleteIcon
    {
        fun onDeleteSuccess(value : String)

    }


    fun loadDirectly(imageView: ImageView, file: File,con : Context) {
        Thread(Runnable {
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(file)
                val bitmap = BitmapFactory.decodeStream(fis)
                (con as AppCompatActivity).runOnUiThread(Runnable {
                    imageView.setImageBitmap(bitmap)
                })
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                if (fis != null) {
                    try {
                        fis!!.close()
                    } catch (e: IOException) {
                        //e.printStackTrace();
                    }

                }
            }
        }).start()

    }


}