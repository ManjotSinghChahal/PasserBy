package com.example.passerby.ui.activities.home.fragments


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide

import com.example.passerby.R
import com.example.passerby.utils.Constants.IMAGE_URL
import com.example.passerby.utils.GlobalHelper
import kotlinx.android.synthetic.main.fragment_full_imageview.*
import kotlinx.android.synthetic.main.fragment_full_imageview.view.*
import kotlinx.android.synthetic.main.user_photo_adapter.view.*


class FullImageview : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_full_imageview, container, false)

        val bundle = arguments
        if (bundle!=null && bundle.containsKey(IMAGE_URL))
        {
            Log.e("bbb",bundle.getString(IMAGE_URL))
            Glide.with(activity as AppCompatActivity).load(bundle.getString(IMAGE_URL))
                .into(view.imgview_fullImageView)
        }

        view.back_fullImageview.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }


}
