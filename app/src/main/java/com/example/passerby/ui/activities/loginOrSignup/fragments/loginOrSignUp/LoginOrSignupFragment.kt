package com.example.passerby.ui.activities.loginOrSignup.fragments.loginOrSignUp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.ui.activities.home.HomeContract
import com.example.passerby.ui.activities.home.HomePresenterImp
import com.example.passerby.ui.activities.home.fragments.advertisement.ReadyToGo
import com.example.passerby.ui.activities.home.fragments.privacyPolicy.PrivacyPolicy
import com.example.passerby.ui.activities.loginOrSignup.fragments.birthday.Birthday
import com.example.passerby.ui.activities.loginOrSignup.fragments.email.EmailFragment
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.Login
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginContract
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginPresenterImp
import com.example.passerby.ui.activities.loginOrSignup.fragments.name.NameFragment
import com.example.passerby.ui.activities.loginOrSignup.fragments.otpVerification.OtpVerification
import com.example.passerby.ui.activities.loginOrSignup.fragments.password.Password
import com.example.passerby.ui.activities.loginOrSignup.fragments.uploadProfilePic.UploadProfilePic
import com.example.passerby.ui.activities.loginOrSignup.fragments.welcomeToPasserby.WelcomeToPasserby
import com.example.passerby.utils.Constants.TERMS_CONDITIONS_DATA
import com.example.passerby.utils.GlobalHelper
import kotlinx.android.synthetic.main.fragment_login_or_signup.view.*


class LoginOrSignupFragment : Fragment() ,LoginContract.LoginSignupView
{
    lateinit var presenter: LoginContract.LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login_or_signup, container, false)

        clickListener(view)
        presenter = LoginPresenterImp(this)


        return view
    }


    fun clickListener(view : View)
    {
        view.signup_loginOrSignup.setOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, EmailFragment())
                .addToBackStack(null).commit()


        }

        view.login_loginOrSignup.setOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, Login())
                .addToBackStack(null).commit()
        }


        view.txtview_termsCondLoginSignup.setOnClickListener {

            GlobalHelper.showProgress(activity as AppCompatActivity)
            presenter.termsConditons()
        }



    }

    override fun onTermsConditionViewSucces(termsConditions: TermsConditionsModel) {


        GlobalHelper.hideProgress()

        val bundle = Bundle()
        bundle.putParcelable(TERMS_CONDITIONS_DATA,termsConditions)
        val frag = PrivacyPolicy()
        frag.arguments = bundle

        (activity as AppCompatActivity). supportFragmentManager.beginTransaction().replace(R.id.container_login, frag).addToBackStack(null).commit()

    }

    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.root_loginSignup, it) }
    }


}
