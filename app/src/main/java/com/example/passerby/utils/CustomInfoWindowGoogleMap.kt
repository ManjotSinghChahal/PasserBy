package com.example.passerby.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.passerby.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class CustomInfoWindowGoogleMap(private val context: Context) : GoogleMap.InfoWindowAdapter {





    override fun getInfoContents(p0: Marker?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.map_infowindow, null)


        return view
    }

    override fun getInfoWindow(p0: Marker?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.map_infowindow, null)


        return view
       }


/*    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val view = (context as Activity).layoutInflater
            .inflate(R.layout.map_infowindow, null)

*//*        val name_tv = view.findViewById(R.id.name)
        val details_tv = view.findViewById(R.id.details)
        val img = view.findViewById(R.id.pic)

        val hotel_tv = view.findViewById(R.id.hotels)
        val food_tv = view.findViewById(R.id.food)
        val transport_tv = view.findViewById(R.id.transport)

        name_tv.setText(marker.title)
        details_tv.setText(marker.snippet)

        val infoWindowData = marker.tag as InfoWindowData?

        val imageId = context.getResources().getIdentifier(
            infoWindowData!!.getImage().toLowerCase(),
            "drawable", context.getPackageName())
        img.setImageResource(imageId)



        hotel_tv.setText(infoWindowData!!.getHotel())
        food_tv.setText(infoWindowData!!.getFood())
        transport_tv.setText(infoWindowData!!.getTransport())*//*

        return view
    }*/

}
