package com.example.passerby.ui.activities.home.fragments.map


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.passerby.R
import com.example.passerby.data.model.userProfile.GetUserProfile
import com.example.passerby.utils.Constants

import com.example.passerby.utils.CustomInfoWindowGoogleMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.view.*




class MapFragment : Fragment() , OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    var profileModel : GetUserProfile? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =   inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bundle = arguments
        if (bundle!=null && bundle.containsKey(Constants.PROFILE))
        {
            profileModel = bundle.getParcelable(Constants.PROFILE)


        }


        clickListener(view)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        this.mMap = googleMap

        val position = LatLng(30.7046, 76.7179)
        val position1 = LatLng(30.7046, 76.8179)
        val position2 = LatLng(30.5046, 76.7179)

     //   for (data in 0..profileModel.)
        mMap?.addMarker(MarkerOptions().position(position).title("Cqlsys Location"))
        mMap?.addMarker(MarkerOptions().position(position1).title("Cqlsys Location"))
        mMap?.addMarker(MarkerOptions().position(position2).title("Cqlsys Location"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10f))

        val CustomInfoWindowGoogleMap = CustomInfoWindowGoogleMap(activity as AppCompatActivity)
        mMap?.setInfoWindowAdapter(CustomInfoWindowGoogleMap)


    }


    fun clickListener(view : View)
    {
        view.back_map.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

    }






}
