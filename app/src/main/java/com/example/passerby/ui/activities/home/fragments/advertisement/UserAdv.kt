package com.example.passerby.ui.activities.home.fragments.advertisement


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.passerby.R
import com.example.passerby.ui.activities.home.fragments.userList.UserList
import kotlinx.android.synthetic.main.fragment_advertisement1.view.*


class UserAdv : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_advertisement1, container, false)


        view.next_userAdv.setOnClickListener {
            activity?.let {
                //   it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit()
                it. supportFragmentManager.beginTransaction().replace(R.id.container_home, MenuAdv()).commit()
            }
        }

        view.adv1_skip.setOnClickListener {
            activity?.let {  it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit() }
        }


        return view
    }


}
