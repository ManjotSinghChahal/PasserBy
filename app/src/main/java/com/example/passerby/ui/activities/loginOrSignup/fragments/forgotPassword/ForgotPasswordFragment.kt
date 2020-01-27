package com.example.passerby.ui.activities.loginOrSignup.fragments.forgotPassword


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.data.model.forgotPassword.ForgotPassword
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginContract
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginPresenterImp
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_forgot_password.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class ForgotPasswordFragment : Fragment(), LoginContract.ForgotPasswordView {

    lateinit var presenter: LoginContract.LoginPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)



        presenter = LoginPresenterImp(this)

        clickListener(view)





        return view
    }


    fun clickListener(view: View) {
        view.send_forgotpassword.setOnClickListener {

            if (Validator.getsInstance().emailValidator(
                    view.forgotpassword_register.text.toString().trim(),
                    view.root_forgotpassword,
                    activity as AppCompatActivity
                )
            ) {
                GlobalHelper.showProgress(activity as AppCompatActivity)
                presenter.forgotPassword(view.forgotpassword_register.text.toString().trim())
            }
        }


        view.back_forgotpassword.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

    }


    override fun onForgotPasswordViewSuccess(forgotPassword: ForgotPassword) {
        GlobalHelper.hideProgress()
        forgotPassword.message.let { GlobalHelper.snackBar(view!!.root_forgotpassword, it) }
        activity?.let { it.onBackPressed() }
    }

    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.root_forgotpassword, it) }
    }


}
