package com.example.passerby.ui.activities.home.fragments.advertisement


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.passerby.R
import com.example.passerby.ui.activities.home.fragments.userList.UserList
import kotlinx.android.synthetic.main.fragment_map_adv.view.*


class MapAdv : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_adv, container, false)

        view.next_mapAdv.setOnClickListener {
            activity?.let {
                //   it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit()
                it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserAdv()).commit()
            }
        }

        view.mapAdv_skip.setOnClickListener {
            activity?.let {  it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit() }
        }

        return view
    }


}
