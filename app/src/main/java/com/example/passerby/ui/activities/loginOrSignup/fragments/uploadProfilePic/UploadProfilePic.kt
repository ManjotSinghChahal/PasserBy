package com.example.passerby.ui.activities.loginOrSignup.fragments.uploadProfilePic


import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

import com.example.passerby.R
import com.example.passerby.data.model.profileData.ProfileData
import com.example.passerby.ui.activities.loginOrSignup.fragments.birthday.Birthday
import com.example.passerby.utils.Constants
import com.example.passerby.utils.ImagepickerFragment
import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_upload_profile_pic.*
import kotlinx.android.synthetic.main.fragment_upload_profile_pic.view.*


class UploadProfilePic : ImagepickerFragment() {

    var image_path = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_upload_profile_pic, container, false)

        clicklistener(view)

        return view
    }

    fun clicklistener(view: View) {
        view.back_uploadProfilePic.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

        view.next_uploadProfilePic.setOnClickListener {

            if (Validator.getsInstance().imageValidator(image_path, view.root_profilePicRegister, activity as AppCompatActivity)) {

                val bundle = arguments
                val profileData : ProfileData? = bundle?.getParcelable(Constants.PROFIE_DATA)
                profileData?.profilePic = image_path

                val frag = Birthday()


                bundle?.putParcelable(Constants.PROFIE_DATA,profileData)
                frag.arguments = bundle


                (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, frag)
                    .addToBackStack(null).commit()
            }


        }

        view.txt_laterUploadPic.setOnClickListener {


            val bundle = arguments
            val profileData : ProfileData? = bundle?.getParcelable(Constants.PROFIE_DATA)
            profileData?.profilePic = image_path

            val frag = Birthday()


            bundle?.putParcelable(Constants.PROFIE_DATA,profileData)
            frag.arguments = bundle


            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.container_login, frag)
                .addToBackStack(null).commit()
        }


        view.img_uploadProfilePic.setOnClickListener {
            getImage(activity, 0)


        }

    }

    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(img_uploadProfilePic)
        image_path=imagePath.toString()

     //   next_uploadProfilePic.setTextColor(ContextCompat.getColor(activity as AppCompatActivity, R.color.colorAppTheme))
     //   next_uploadProfilePic.setBackgroundResource(R.drawable.rounded_outlining_apptheme)

    }


    override fun onResume() {
        super.onResume()
        if (!image_path.isEmpty())
        {
            Glide.with(this).load(image_path).into(img_uploadProfilePic)
         //   next_uploadProfilePic.setTextColor(ContextCompat.getColor(activity as AppCompatActivity, R.color.colorAppTheme))
        //    next_uploadProfilePic.setBackgroundResource(R.drawable.rounded_outlining_apptheme)

        }
    }

}


