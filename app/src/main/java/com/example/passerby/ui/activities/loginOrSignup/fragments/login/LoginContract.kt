package com.example.passerby.ui.activities.loginOrSignup.fragments.login

import com.example.passerby.data.model.addProfile.AddProfile
import com.example.passerby.data.model.forgotPassword.ForgotPassword
import com.example.passerby.data.model.loginModel.LoginModel
import com.example.passerby.data.model.otpModel.OtpModel
import com.example.passerby.data.model.registerEmailModel.RegisterEmailModel
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.ui.baseClasses.BaseContract

interface LoginContract : BaseContract
{

    interface LoginPresenter
    {
        fun login(email : String,password : String)
        fun forgotPassword(email : String)
        fun email_register(email : String)
        fun otp(email : String,otp : String)
        fun termsConditons()
        fun addProfile(email : String,password : String,name : String,dob : String,image : String,latitude : String,longitude : String,location : String)
    }

    interface LoginInteractor
    {
        fun login(email : String,password : String, callback : OnLoginCompleteListener)
        fun forgotPassword(email : String, callback : OnLoginCompleteListener)
        fun email_register(email : String, callback : OnLoginCompleteListener)
        fun otp(email : String,otp : String, callback : OnLoginCompleteListener)
        fun termsConditons(callback : OnLoginCompleteListener)
        fun addProfile(email : String,password : String,name : String,dob : String,image : String,latitude : String,longitude : String,location : String, callback : OnLoginCompleteListener)
    }


    interface OnLoginCompleteListener : BaseContract.BaseOnCompleteListener
    {
       fun onLoginSucces(login: LoginModel?)
       fun onForgotPasswordSucces(forgotPassword: ForgotPassword)
       fun onEmailRegisterSucces(registeremail: RegisterEmailModel?)
       fun onOtpSucces(otp: OtpModel?)
        fun onTermsConditionSucces(termsConditions: TermsConditionsModel)
       fun onaddProfileSucces(addprofile: AddProfile?)
    }

    interface LoginView : BaseContract.BaseView
    {
        fun onLoginViewSuccess(login: LoginModel)
    }

    interface ForgotPasswordView : BaseContract.BaseView
    {
        fun onForgotPasswordViewSuccess(forgotPassword: ForgotPassword)
    }


    interface EmailRegisterView : BaseContract.BaseView
    {
        fun onEmailRegisterViewSuccess(register_email: RegisterEmailModel)
        fun onOtpViewSuccess(otp: OtpModel)
    }


    interface AddProfileView : BaseContract.BaseView
    {
        fun onaddProfileViewSuccess(addprofile: AddProfile)
    }

    interface LoginSignupView : BaseContract.BaseView
    {
        fun onTermsConditionViewSucces(termsConditions: TermsConditionsModel)
    }



}