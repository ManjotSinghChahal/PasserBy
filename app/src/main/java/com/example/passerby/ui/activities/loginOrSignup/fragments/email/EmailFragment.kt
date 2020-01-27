package com.example.passerby.ui.activities.loginOrSignup.fragments.email


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.data.model.loginModel.LoginModel
import com.example.passerby.data.model.otpModel.OtpModel
import com.example.passerby.data.model.profileData.ProfileData
import com.example.passerby.data.model.registerEmailModel.RegisterEmailModel
import com.example.passerby.ui.activities.loginOrSignup.fragments.birthday.Birthday
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.Login
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginContract
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginPresenterImp
import com.example.passerby.ui.activities.loginOrSignup.fragments.otpVerification.OtpVerification
import com.example.passerby.ui.activities.loginOrSignup.fragments.password.Password
import com.example.passerby.utils.Constants
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_birthday.*
import kotlinx.android.synthetic.main.fragment_email.*
import kotlinx.android.synthetic.main.fragment_email.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class EmailFragment : Fragment() , LoginContract.EmailRegisterView{


    lateinit var presenter :  LoginContract.LoginPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_email, container, false)


        presenter  = LoginPresenterImp(this)

        clickListener(view)



        return view
    }

    fun clickListener(view : View)
    {
        view.next_email.setOnClickListener {


             if (Validator.getsInstance().emailValidator(email_register.text.toString().trim(),view.root_emailRegister,activity as AppCompatActivity))
               {

                   GlobalHelper.showProgress(activity as AppCompatActivity)
                   presenter.email_register(view.email_register.text.toString().trim())
               }

        }

        view.back_email.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

        view.login_email.setOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, Login())
                .addToBackStack(null).commit()
        }

    }

    override fun onEmailRegisterViewSuccess(register_email: RegisterEmailModel) {
        GlobalHelper.hideProgress()


        if (register_email.body.message.equals("Verified User"))
        {
            val profileData = ProfileData()
            profileData.email = email_register.text.toString().trim()

            val frag = Password()

            val bundle = Bundle()
            bundle.putParcelable(Constants.PROFIE_DATA,profileData)
            frag.arguments = bundle

            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, frag)
                .addToBackStack(null).commit()
        }
        else
        {
            register_email.body.message.let { GlobalHelper.snackBar(view!!.root_emailRegister, it) }
            val profileData = ProfileData()
            profileData.email = email_register.text.toString().trim()

            val frag = OtpVerification()

            val bundle = Bundle()
            bundle.putParcelable(Constants.PROFIE_DATA,profileData)
            frag.arguments = bundle

            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container_login, frag)
                .addToBackStack(null).commit()
        }



    }
    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.root_emailRegister, it) }
    }

    override fun onOtpViewSuccess(otp: OtpModel) {
    }


}
