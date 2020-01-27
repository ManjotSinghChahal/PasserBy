package com.example.passerby.ui.activities.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.example.passerby.R
import com.example.passerby.ui.activities.home.fragments.advertisement.PasserbyAdv
import com.example.passerby.ui.activities.home.fragments.userList.UserList
import com.example.passerby.ui.baseClasses.BaseActivity

import com.example.passerby.utils.Constants
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.passerby.ui.baseClasses.App
import com.example.passerby.utils.BackgroundService
import com.example.passerby.utils.SocketManager
import io.socket.client.Socket
import org.json.JSONObject
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.passerby.utils.SharedPrefUtil


class Home : BaseActivity() {
    var updateUIReciver: BroadcastReceiver? = null

    private val REQUEST_PERMISSION_LOCATION = 10
    var mSocket: Socket? = null
    var mSocketManager: SocketManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        setContentView(R.layout.home)


        val intent: Intent = intent
        if (intent != null && intent.hasExtra(Constants.HOME_ENTRY_TYPE)) {
            if (intent.getStringExtra(Constants.HOME_ENTRY_TYPE).equals(Constants.LOGIN_TYPE))
                supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit()
            else if (intent.getStringExtra(Constants.HOME_ENTRY_TYPE).equals(Constants.REGISTER_TYPE))
                supportFragmentManager.beginTransaction().replace(R.id.container_home, PasserbyAdv()).commit()

        }

        val app = application as App
        mSocket = app.getSocket()
        mSocketManager = App.instance!!.getSocketManager()





        if (checkPermissionForLocation(this)) {
            startService(Intent(this, BackgroundService::class.java))
        }


        val filter = IntentFilter()
        filter.addAction("service.to.activity.transfer")


        LocalBroadcastManager.getInstance(this).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val latitude = intent.getStringExtra("lat")
                    val longitude = intent.getStringExtra("lng")

                    if (latitude != null && longitude != null) {

                        val jsonObject = JSONObject()
                        jsonObject.put("user_id", SharedPrefUtil.getInstance()?.getUserId())
                        jsonObject.put("latitude", latitude)
                        jsonObject.put("longitude", longitude)
                        mSocketManager!!.onLocationUpdate(jsonObject)
                    }
                }
            }, IntentFilter("ACTION_LOCATION_BROADCAST")
        )


    }


    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, BackgroundService::class.java))
    }


    fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                // Show the permission request
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                false
            }
        } else {
            true
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startService(Intent(this, BackgroundService::class.java))

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


}