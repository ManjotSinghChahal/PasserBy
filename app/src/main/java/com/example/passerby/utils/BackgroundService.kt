package com.example.passerby.utils

import android.Manifest
import android.Manifest.*
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener


class BackgroundService : Service() ,LocationListener
  {



      override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
      }

      override fun onProviderEnabled(provider: String?) {
      }

      override fun onProviderDisabled(provider: String?) {
      }


      private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
      private val INTERVAL: Long = 2000
      private val FASTEST_INTERVAL: Long = 1000
      lateinit var mLastLocation: Location
      internal lateinit var mLocationRequest: LocationRequest




    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY
    }

    override fun onCreate() {

        mLocationRequest = LocationRequest()

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlert()
        }

          startLocationUpdates()


    }


      override fun onDestroy() {
          super.onDestroy()
          stoplocationUpdates()
      }



      override fun onLocationChanged(location: Location) {
        //   var msg = "Updated_Location_Latitude " + location.longitude.toString() + location.longitude;
          sendMessageToUI(location.latitude.toString(), location.longitude.toString())

      }


      //---------------------------------------------


      private fun showAlert() {
          val dialog = AlertDialog.Builder(this)
          dialog.setTitle("Enable Location")
              .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
              .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                  val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                  startActivity(myIntent)
              })
              .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
          dialog.show()
      }

      protected fun startLocationUpdates() {

          // Create the location request to start receiving updates

          mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
          mLocationRequest!!.setInterval(1000)
          mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

          // Create LocationSettingsRequest object using location request
          val builder = LocationSettingsRequest.Builder()
          builder.addLocationRequest(mLocationRequest!!)
          val locationSettingsRequest = builder.build()

          val settingsClient = LocationServices.getSettingsClient(this)
          settingsClient.checkLocationSettings(locationSettingsRequest)

          mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
          // new Google API SDK v11 uses getFusedLocationProviderClient(this)
          if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


              return
          }
          mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
              Looper.myLooper())
      }


      private val mLocationCallback = object : LocationCallback() {
          override fun onLocationResult(locationResult: LocationResult) {
              // do work here
              locationResult.lastLocation
              onLocationChanged(locationResult.lastLocation)
          }
      }




      private fun stoplocationUpdates() {
          mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
      }



      private fun sendMessageToUI(lat: String, lng: String) {

          val intent = Intent("ACTION_LOCATION_BROADCAST")
          intent.putExtra("lat", lat)
          intent.putExtra("lng", lng)
          LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
      }


  }





















/*private fun initializeLocationManager() {
    Log.e(
        TAG,
        "initializeLocationManager - LOCATION_INTERVAL: $LOCATION_INTERVAL LOCATION_DISTANCE: $LOCATION_DISTANCE"
    )
    if (mLocationManager == null) {
        mLocationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}*/



/*initializeLocationManager()

try {
    mLocationManager!!.requestLocationUpdates(
        LocationManager.PASSIVE_PROVIDER,
        LOCATION_INTERVAL.toLong(),
        LOCATION_DISTANCE,
        mLocationListeners[0]
    )
} catch (ex: java.lang.SecurityException) {
    Log.i(TAG, "fail to request location update, ignore", ex)
} catch (ex: IllegalArgumentException) {
    Log.d(TAG, "network provider does not exist, " + ex.message)
}*/



/*override fun onDestroy() {
    Log.e(TAG, "onDestroy")
    super.onDestroy()
    if (mLocationManager != null) {
        for (i in mLocationListeners.indices) {
            try {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                mLocationManager!!.removeUpdates(mLocationListeners[i])
            } catch (ex: Exception) {
                Log.i(TAG, "fail to remove location listener, ignore", ex)
            }

        }
    }
}*/




/*
private val TAG = "MyLocationService"
private var mLocationManager: LocationManager? = null
private val LOCATION_INTERVAL = 1000
private val LOCATION_DISTANCE = 10f

inner class LocationListener(provider: String) : android.location.LocationListener {
    internal var mLastLocation: Location

    init {
        Log.e(TAG, "LocationListener $provider")
        mLastLocation = Location(provider)
    }

    override fun onLocationChanged(location: Location) {
        Log.e(TAG, "onLocationChanged: $location")
        mLastLocation.set(location)
    }

    override fun onProviderDisabled(provider: String) {
        Log.e(TAG, "onProviderDisabled: $provider")
    }

    override fun onProviderEnabled(provider: String) {
        Log.e(TAG, "onProviderEnabled: $provider")
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        Log.e(TAG, "onStatusChanged: $provider")
    }
}


var mLocationListeners = arrayOf(LocationListener(LocationManager.PASSIVE_PROVIDER))



*/


























/*
class BackgroundService : Service() {
    private val binder = LocationServiceBinder()
    private val TAG = "BackgroundService"
    private var mLocationListener: LocationListener? = null
    private var mLocationManager: LocationManager? = null
    private val notificationManager: NotificationManager? = null

    private val LOCATION_INTERVAL = 500
    private val LOCATION_DISTANCE = 10

    private val notification: Notification
        @RequiresApi(Build.VERSION_CODES.O)
        get() {

            val channel = NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_DEFAULT)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)

            val builder = Notification.Builder(getApplicationContext(), "channel_01").setAutoCancel(true)
            return builder.build()
        }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private inner class LocationListener(provider: String) : android.location.LocationListener {
        private val lastLocation: Location? = null
        private val TAG = "LocationListener"
        private var mLastLocation: Location? = null

        init {
            mLastLocation = Location(provider)
        }

        override fun onLocationChanged(location: Location) {
            mLastLocation = location
            Log.i(TAG, "LocationChanged: $location")
        }

        override fun onProviderDisabled(provider: String) {
            Log.e(TAG, "onProviderDisabled: $provider")
        }

        override fun onProviderEnabled(provider: String) {
            Log.e(TAG, "onProviderEnabled: $provider")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.e(TAG, "onStatusChanged: $status")
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        startForeground(12345678, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLocationManager != null) {
            try {
                mLocationManager!!.removeUpdates(mLocationListener)
            } catch (ex: Exception) {
                Log.i(TAG, "fail to remove location listners, ignore", ex)
            }

        }
    }

    private fun initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = getApplicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        }
    }

    fun startTracking() {
        initializeLocationManager()
        mLocationListener = LocationListener(LocationManager.GPS_PROVIDER)

        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_INTERVAL.toLong(),
                LOCATION_DISTANCE.toFloat(),
                mLocationListener
            )

        } catch (ex: java.lang.SecurityException) {
            // Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (ex: IllegalArgumentException) {
            // Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

    }

    fun stopTracking() {
        this.onDestroy()
    }


    inner class LocationServiceBinder : Binder() {
        val service: BackgroundService
            get() = this@BackgroundService
    }

}*/
