package com.example.passerby.ui.activities.loginOrSignup

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.passerby.R
import com.example.passerby.ui.activities.loginOrSignup.fragments.email.EmailFragment
import com.example.passerby.ui.activities.loginOrSignup.fragments.loginOrSignUp.LoginOrSignupFragment
import com.example.passerby.ui.activities.loginOrSignup.fragments.otpVerification.OtpVerification
import com.example.passerby.ui.baseClasses.BaseActivity
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.EDIT_EMAIL
import com.example.passerby.utils.Constants.LoginSignup_ENTRY_TYPE

class LoginOrSignup : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        setContentView(R.layout.login_or_signup)


        val intent = intent
        if (intent!=null && intent.hasExtra(LoginSignup_ENTRY_TYPE))
        {
            val email = intent.getStringExtra(Constants.TYPE_EMAIL)
            val frag = OtpVerification()
            val bundle = Bundle()
            bundle.putString(EDIT_EMAIL,email)
            frag.arguments = bundle

            supportFragmentManager.beginTransaction().replace(R.id.container_login, frag).commit()
        }

        else
        supportFragmentManager.beginTransaction().replace(R.id.container_login, LoginOrSignupFragment()).commit()

    }
}