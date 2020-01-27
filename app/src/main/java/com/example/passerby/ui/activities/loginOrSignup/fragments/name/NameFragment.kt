package com.example.passerby.ui.activities.loginOrSignup.fragments.name


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.data.model.profileData.ProfileData
import com.example.passerby.ui.activities.loginOrSignup.fragments.birthday.Birthday
import com.example.passerby.ui.activities.loginOrSignup.fragments.uploadProfilePic.UploadProfilePic
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_birthday.view.*
import kotlinx.android.synthetic.main.fragment_name.*
import kotlinx.android.synthetic.main.fragment_name.view.*


class NameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_name, container, false)

        clickListener(view)

        return view
    }

    fun clickListener(view : View)
    {
        view.next_name.setOnClickListener {

            if (Validator.getsInstance().nameValidator(name_register.text.toString().trim(),root_nameRegister,activity as AppCompatActivity))
            {
                val bundle = arguments
                val profileData : ProfileData? = bundle?.getParcelable(Constants.PROFIE_DATA)
                profileData?.name = name_register.text.toString().trim()

                val frag = UploadProfilePic()
                bundle?.putParcelable(Constants.PROFIE_DATA,profileData)
                frag.arguments = bundle

                (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, frag)
                    .addToBackStack(null).commit()
            }


        }

        view.back_name.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

    }
}
