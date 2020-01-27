package com.example.passerby.ui.activities.loginOrSignup.fragments.welcomeToPasserby


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.passerby.R
import com.example.passerby.ui.activities.home.Home
import com.example.passerby.ui.activities.home.fragments.advertisement.*
import com.example.passerby.utils.Constants
import kotlinx.android.synthetic.main.fragment_welcome_to_passerby.view.*


class WelcomeToPasserby : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_welcome_to_passerby, container, false)

        view.next_welToPassby.setOnClickListener {


            val intent : Intent = Intent(activity, Home::class.java)
            intent.putExtra(Constants.HOME_ENTRY_TYPE,Constants.REGISTER_TYPE)
            startActivity(intent)
            activity?.finish()
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)


        }



        return view
    }


}
