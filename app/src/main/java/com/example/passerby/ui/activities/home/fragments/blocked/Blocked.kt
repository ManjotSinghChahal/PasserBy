package com.example.passerby.ui.activities.home.fragments.blocked


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.passerby.R
import kotlinx.android.synthetic.main.fragment_blocked.view.*


class Blocked : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_blocked, container, false)



        view.blocked_ok.setOnClickListener {

            fragmentManager?.popBackStack()
            fragmentManager?.popBackStack()
            fragmentManager?.popBackStack()

           // activity?.let { it.onBackPressed() }
        }


        return view
    }


}
