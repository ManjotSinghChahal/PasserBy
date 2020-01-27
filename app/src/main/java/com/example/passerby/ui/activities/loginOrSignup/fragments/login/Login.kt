package com.example.passerby.ui.activities.loginOrSignup.fragments.login


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.data.model.loginModel.LoginModel
import com.example.passerby.ui.activities.home.Home
import com.example.passerby.ui.activities.loginOrSignup.fragments.email.EmailFragment
import com.example.passerby.ui.activities.loginOrSignup.fragments.forgotPassword.ForgotPasswordFragment
import com.example.passerby.utils.Constants
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.SharedPrefUtil

import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class Login : Fragment() , LoginContract.LoginView  {



    lateinit var presenter :  LoginContract.LoginPresenter
    var user_email : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_login, container, false)

        presenter  = LoginPresenterImp(this)

        clickListener(view)

        return view
    }


    fun clickListener(view : View)
    {
        view.login_login.setOnClickListener {

            if (Validator.getsInstance().loginValidator(email_login.text.toString().trim(),password_email.text.toString().trim(),view.rootLin_login,activity as AppCompatActivity))
            {
                GlobalHelper.showProgress(activity as AppCompatActivity)
                presenter.login(view.email_login.text.toString().trim(),password_email.text.toString().trim())
            }
    }


        view.text_forgotPassword.setOnClickListener {

            activity?.let {  it.supportFragmentManager.beginTransaction().replace(R.id.container_login, ForgotPasswordFragment())
                .addToBackStack(null).commit() }

        }

        view.create_email_login.setOnClickListener {
            activity?.let {  it.supportFragmentManager.beginTransaction().replace(R.id.container_login, EmailFragment())
                .addToBackStack(null).commit() }
        }

        view.back_login.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

    }





    override fun onLoginViewSuccess(login: LoginModel) {
        GlobalHelper.hideProgress()

        login.message.let { GlobalHelper.snackBar(view!!.rootLin_login, it) }


        SharedPrefUtil.getInstance()?.saveEmail(email_login.text.toString().trim())
        SharedPrefUtil.getInstance()?.setLogin(true)
        SharedPrefUtil.getInstance()?.saveAuthToken(login.body.token)
        SharedPrefUtil.getInstance()?.saveUserId(login.body.id.toString())


        val intent : Intent = Intent(activity, Home::class.java)
        intent.putExtra(Constants.HOME_ENTRY_TYPE, Constants.LOGIN_TYPE)
        startActivity(Intent(intent))
        activity?.finish()
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }



    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.rootLin_login, it) }
    }




}
