package com.example.passerby.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class CustomClusterItem(d: Double, d1: Double, s: String, s1: String) : ClusterItem {
    private var mPosition: LatLng? = null
    private var mTitle: String? = null
    private var mSnippet: String? = null


    init {
        this.mPosition =  LatLng(d,d1)
        this.mTitle =  s
        this.mSnippet =  s1
    }



    override fun getSnippet(): String? {
        return mSnippet    }

    override fun getTitle(): String? {
        return mTitle    }

    override fun getPosition(): LatLng? {
        return mPosition
    }
}