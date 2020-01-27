package com.example.passerby.ui.activities.home.fragments.myQuote


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.passerby.R
import kotlinx.android.synthetic.main.fragment_my_quotes.view.*



class MyQuotes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      val view = inflater.inflate(R.layout.fragment_my_quotes, container, false)



        clickListener(view)
        return view
    }


    fun clickListener(view : View)
    {

        view.back_myQuotes.setOnClickListener {

        }

    }


}
