package com.example.passerby.ui.activities.home.fragments.advertisement


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.passerby.R
import com.example.passerby.ui.activities.home.fragments.userList.UserList
import kotlinx.android.synthetic.main.fragment_ready_to_go.view.*


class ReadyToGo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ready_to_go, container, false)


        view.finish_readyToGo.setOnClickListener {
            activity?.let {
                //   it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit()
                it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit()
            }
        }



        return view
    }


}
