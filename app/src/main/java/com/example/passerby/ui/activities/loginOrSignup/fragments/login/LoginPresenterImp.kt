package com.example.passerby.ui.activities.loginOrSignup.fragments.login

import android.util.Log
import com.example.passerby.R
import com.example.passerby.data.model.addProfile.AddProfile
import com.example.passerby.data.model.forgotPassword.ForgotPassword
import com.example.passerby.data.model.loginModel.LoginModel
import com.example.passerby.data.model.otpModel.OtpModel
import com.example.passerby.data.model.registerEmailModel.RegisterEmailModel
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.ui.baseClasses.App

class LoginPresenterImp() : LoginContract.LoginPresenter, LoginContract.OnLoginCompleteListener {

    lateinit var interactor: LoginContract.LoginInteractor
     var loginView: LoginContract.LoginView? = null
     var emailRegister_view: LoginContract.EmailRegisterView? = null
    // var otp_view: LoginContract.EmailRegisterView? = null
     var addprofile_view: LoginContract.AddProfileView? = null
     var forgotPasswordView: LoginContract.ForgotPasswordView? = null
     var loginSignupView: LoginContract.LoginSignupView? = null

    init {

    }


    //------------------constructor for each view----------------------
    constructor(login_view: LoginContract.LoginView) : this() {
        interactor = LoginInteractorImp()

        loginView = login_view
    }

    constructor(loginSignup_view: LoginContract.LoginSignupView) : this() {
        interactor = LoginInteractorImp()

        loginSignupView = loginSignup_view
    }

    constructor(forgotPassword_view: LoginContract.ForgotPasswordView) : this() {
        interactor = LoginInteractorImp()

        forgotPasswordView = forgotPassword_view
    }

    constructor(email_register_view: LoginContract.EmailRegisterView) : this() {
        interactor = LoginInteractorImp()
        emailRegister_view = email_register_view
    }


    constructor(addprofileview: LoginContract.AddProfileView) : this() {
        interactor = LoginInteractorImp()
        addprofile_view = addprofileview
    }
    //--------------passing data to interactor----------------------
    override fun login(email: String, password: String) {

        if (App.hasNetwork()) {
            interactor.login(email, password, this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }

    }

    override fun termsConditons() {
        if (App.hasNetwork()) {
            interactor.termsConditons(this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun email_register(email: String) {
        if (App.hasNetwork()) {
            interactor.email_register(email, this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun otp(email: String, otp: String) {
        if (App.hasNetwork()) {
            interactor.otp(email,otp, this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun addProfile(email: String, password: String, name: String, dob: String, image: String, latitude: String, longitude: String, location: String) {

        if (App.hasNetwork()) {
            interactor.addProfile(email,password,name,dob,image,latitude,longitude,location,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun forgotPassword(email: String) {
        if (App.hasNetwork()) {
            interactor.forgotPassword(email,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }


    //---------------------onApi-success-----------------------------
    override fun onLoginSucces(login: LoginModel?) {
        login?.let { loginView?.onLoginViewSuccess(it) }
    }

    override fun onEmailRegisterSucces(registeremail: RegisterEmailModel?) {
        registeremail?.let { emailRegister_view?.onEmailRegisterViewSuccess(it) }
    }

    override fun onOtpSucces(otp: OtpModel?) {
        otp?.let { emailRegister_view?.onOtpViewSuccess(it) }
    }

    override fun onaddProfileSucces(addprofile: AddProfile?) {
      addprofile?.let { addprofile_view?.onaddProfileViewSuccess(it) }
    }


    override fun onForgotPasswordSucces(forgotPassword: ForgotPassword) {
     forgotPassword.let { forgotPasswordView?.onForgotPasswordViewSuccess(it) }
    }

    override fun onTermsConditionSucces(termsConditions: TermsConditionsModel) {
        termsConditions?.let { loginSignupView?.onTermsConditionViewSucces(it) }
    }



    //-----------------on api failure-----------------------
    override fun onFailure(error: String) {

        if(loginView!=null)
         loginView?.onFailure(error)
        else if(emailRegister_view!=null)
            emailRegister_view?.onFailure(error)
        else if(forgotPasswordView!=null)
            forgotPasswordView?.onFailure(error)
        else if(loginSignupView!=null)
            loginSignupView?.onFailure(error)
    }


}