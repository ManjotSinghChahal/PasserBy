package com.example.passerby.ui.activities.splash

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.example.passerby.R
import com.example.passerby.ui.activities.home.Home
import com.example.passerby.ui.activities.loginOrSignup.LoginOrSignup
import com.example.passerby.ui.baseClasses.BaseActivity
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.SCREEN_HEIGHT_DP
import com.example.passerby.utils.Constants.SCREEN_HEIGHT_PX
import com.example.passerby.utils.Constants.SCREEN_WIDTH_DP
import com.example.passerby.utils.Constants.SCREEN_WIDTH_PX
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.GrantPermissions
import com.example.passerby.utils.SharedPrefUtil

class Splash : BaseActivity() , SplashView
{
    /*private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1500 //2 seconds*/

    private val PermissionsRequestCode = 101
    lateinit var presenter: SplashPresenter
    lateinit var managePermissions: GrantPermissions
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1500 //2 seconds


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash)


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        SCREEN_WIDTH_PX = displayMetrics.widthPixels
        SCREEN_HEIGHT_PX = displayMetrics.heightPixels
        SCREEN_WIDTH_DP  = GlobalHelper.convertPixelsToDp(SCREEN_WIDTH_PX.toFloat(),this).toInt()
        SCREEN_HEIGHT_DP  = GlobalHelper.convertPixelsToDp(SCREEN_HEIGHT_PX.toFloat(),this).toInt()




        presenter = SplashPresenter(this, SplashInteractor())

        val list = listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        // Initialize a new instance of ManagePermissions class
        managePermissions = GrantPermissions(this, list, PermissionsRequestCode,this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
        else
            presenter.onTimeOut()



    }

    override fun onHandleTimeout() {

        mDelayHandler = Handler()
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {



        when (requestCode) {
            PermissionsRequestCode -> {
                val isPermissionsGranted = managePermissions
                    .processPermissionsResult(requestCode, permissions, grantResults)

                presenter.onTimeOut()

                if (isPermissionsGranted) {
                    //  toast("Permissions granted.")
                } else {
                    //  toast("Permissions denied.")
                }
                return
            }
        }

    }

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            if (SharedPrefUtil.getInstance()?.isLogin()!!)
            {
                val intent : Intent = Intent(applicationContext, Home::class.java)
                intent.putExtra(Constants.HOME_ENTRY_TYPE, Constants.LOGIN_TYPE)
               // val intent = Intent(applicationContext, Home::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            else
            {
                val intent = Intent(applicationContext, LoginOrSignup::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }



        }
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        if (mDelayHandler != null)
            mDelayHandler!!.removeCallbacks(mRunnable)
    }

    override fun onRestart() {
        mDelayHandler = Handler()
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
        super.onRestart()
    }


}

