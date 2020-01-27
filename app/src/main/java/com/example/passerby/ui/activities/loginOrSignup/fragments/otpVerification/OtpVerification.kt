package com.example.passerby.ui.activities.loginOrSignup.fragments.otpVerification


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.ui.activities.loginOrSignup.fragments.password.Password
import kotlinx.android.synthetic.main.fragment_otp_verification.view.*
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.example.passerby.data.model.otpModel.OtpModel
import com.example.passerby.data.model.profileData.ProfileData
import com.example.passerby.data.model.registerEmailModel.RegisterEmailModel
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginContract
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginPresenterImp
import com.example.passerby.ui.activities.loginOrSignup.fragments.loginOrSignUp.LoginOrSignupFragment
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.EDIT_EMAIL
import com.example.passerby.utils.Constants.PROFIE_DATA
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.PinEntry
import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_email.view.*
import kotlinx.android.synthetic.main.fragment_otp_verification.*








class OtpVerification : Fragment() , LoginContract.EmailRegisterView {



    var otpLen : Int = 0
    var otp : String = ""
    var email : String = ""
    lateinit var presenter : LoginContract.LoginPresenter
    var bundle : Bundle? = null
    var statusOtp : Boolean = false  // to show alert while updating email



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_otp_verification, container, false)


        presenter = LoginPresenterImp(this)

         initializer(view)
         clickListener(view)




        return view
    }

    fun initializer(view : View)
    {

         bundle = arguments
        if (bundle!=null && bundle!!.containsKey(PROFIE_DATA))
        {
            val profile : ProfileData? =   bundle?.getParcelable(Constants.PROFIE_DATA)
            email = profile!!.email
            statusOtp = false
        }
        else if (bundle!=null && bundle!!.containsKey(EDIT_EMAIL))
        {
            view.back_otp.visibility = View.GONE
            email = bundle!!.getString(EDIT_EMAIL)
            statusOtp = true
        }



    }

    fun clickListener(view : View)
    {
        view.submit_otp
            .setOnClickListener {

                if (Validator.getsInstance().otpValidator(otpLen,root_Otp,activity as AppCompatActivity))
                {
                    GlobalHelper.showProgress(activity as AppCompatActivity)
                    presenter.otp(email,otp)
                }

            }


        view.resend_email.setOnClickListener {
            GlobalHelper.showProgress(activity as AppCompatActivity)
            presenter.email_register(email)
        }

        view.back_otp.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }


        val txtPinEntry = view.findViewById(R.id.txt_pin_entry) as PinEntry
        txtPinEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {

                otpLen= s.length
                otp = s.toString()

            }
        })




    }


    override fun onOtpViewSuccess(otp: OtpModel) {

        GlobalHelper.hideProgress()
     //   otp.body.message.let { GlobalHelper.snackBar(view!!.root_Otp, it) }

        //---------exe when update email------------
        if (statusOtp)
        {

            MaterialDialog.Builder(activity as AppCompatActivity)
                .title(R.string.signin_again)
                .positiveText(R.string.ok)
                .cancelable(false)
                .onPositive(MaterialDialog.SingleButtonCallback { dialog, which ->
                    (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, LoginOrSignupFragment())
                        .addToBackStack(null).commit()
                })
                .show()

        }
        else
        {
            val frag = Password()
            frag.arguments = bundle
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, frag)
                .addToBackStack(null).commit()
        }


    }

    override fun onEmailRegisterViewSuccess(register_email: RegisterEmailModel) {
        GlobalHelper.hideProgress()
        register_email.body.message.let { GlobalHelper.snackBar(view!!.root_Otp, it) }    }

    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.root_Otp, it) }
    }




}
