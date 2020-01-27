package com.example.passerby.ui.activities.loginOrSignup.fragments.birthday


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import kotlinx.android.synthetic.main.fragment_birthday.view.*
import java.util.*

import android.widget.LinearLayout
import com.example.passerby.data.model.addProfile.AddProfile
import com.example.passerby.data.model.profileData.ProfileData
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginContract
import com.example.passerby.ui.activities.loginOrSignup.fragments.login.LoginPresenterImp
import com.example.passerby.ui.activities.loginOrSignup.fragments.welcomeToPasserby.WelcomeToPasserby
import com.example.passerby.utils.Constants
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.SharedPrefUtil
import com.example.passerby.utils.Validator
import kotlinx.android.synthetic.main.fragment_birthday.*


class Birthday : Fragment() , LoginContract.AddProfileView {


    var datePicker : TextView? = null
    var root_dob : LinearLayout? = null
    lateinit var presenter :  LoginContract.LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_birthday, container, false)

        datePicker = view.findViewById(R.id.selectDatePicker)
        root_dob = view.findViewById(R.id.root_dob)
        clickListener(view)

         presenter = LoginPresenterImp(this)

        return view
    }


    fun clickListener(view : View)
    {


        view.back_birthday.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }


        view.finish_birthday.setOnClickListener {

            if (Validator.getsInstance().bdayValidator(selectDatePicker.text.toString().trim(),view.root_dob,activity as AppCompatActivity))
            {

                val bundle = arguments
                val profile : ProfileData? = bundle?.getParcelable(Constants.PROFIE_DATA)
                profile?.dob = selectDatePicker.text.toString().trim()


                GlobalHelper.showProgress(activity as AppCompatActivity)
                presenter.addProfile(profile!!.email,profile?.password,profile?.name,profile?.dob,profile?.profilePic,Constants.DEVICE_LAT,Constants.DEVICE_LNG,Constants.DEVICE_LOC)



            }


        }

        datePicker?.setOnClickListener {


            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val date_picker = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox



                val userAge = GregorianCalendar(year, month, day)
                val minAdultAge = GregorianCalendar()
                minAdultAge.add(Calendar.YEAR, -17)
                if (minAdultAge.before(userAge)) {
                    root_dob?.let { it1 -> GlobalHelper.snackBar(it1, getString(R.string.dob_invalid)) }
                }
                else
                {
                    datePicker?.text = "" + dayOfMonth + "-" + monthOfYear + "-" + year
                }

            }, year, month, day)

            date_picker.show()

        }

    }

    override fun onaddProfileViewSuccess(addprofile: AddProfile) {

        GlobalHelper.hideProgress()
        addprofile.message.let { GlobalHelper.snackBar(view!!.root_dob, it) }


        SharedPrefUtil.getInstance()?.saveEmail(addprofile.body.email)
        SharedPrefUtil.getInstance()?.setLogin(true)
        SharedPrefUtil.getInstance()?.saveAuthToken(addprofile.body.token)
        SharedPrefUtil.getInstance()?.saveUserId(addprofile.body.id.toString())

                activity?.let {
            //   it. supportFragmentManager.beginTransaction().replace(R.id.container_home, UserList()).commit()
            it. supportFragmentManager.beginTransaction().replace(R.id.container_login, WelcomeToPasserby()).commit()
        }


    }

    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.root_dob, it) }    }


}
