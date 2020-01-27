package com.example.passerby.ui.activities.loginOrSignup.fragments.password


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.data.model.profileData.ProfileData
import com.example.passerby.ui.activities.loginOrSignup.fragments.name.NameFragment
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_password.view.*


class Password : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_password, container, false)

        clickListener(view)



        return view
    }


    fun clickListener(view : View)
    {
        view.next_password.setOnClickListener {


            if (Validator.getsInstance().passwordValidator(password_register.text.toString().trim(),root_passwordRegister,activity as AppCompatActivity))
            {

                val bundle = arguments
                val profileData : ProfileData? = bundle?.getParcelable(Constants.PROFIE_DATA)
                profileData?.password = password_register.text.toString().trim()

                val frag = NameFragment()
                bundle?.putParcelable(Constants.PROFIE_DATA,profileData)
                frag.arguments = bundle


                (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, frag)
                    .addToBackStack(null).commit()
            }
            }

        view.back_password.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

    }
}
