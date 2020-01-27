package com.example.passerby.ui.activities.home.fragments.setting


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import com.example.passerby.R
import com.example.passerby.data.model.getSetting.GetSetting
import com.example.passerby.data.model.updateSetting.UpdateSetting
import com.example.passerby.ui.activities.home.HomeContract
import com.example.passerby.ui.activities.home.HomePresenterImp
import com.example.passerby.ui.activities.loginOrSignup.LoginOrSignup
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.SharedPrefUtil
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import android.text.InputType
import androidx.annotation.NonNull
import com.afollestad.materialdialogs.DialogAction

import com.afollestad.materialdialogs.MaterialDialog
import com.example.passerby.data.model.logout.Logout
import com.example.passerby.data.model.privacyPolicy.PrivacyPolicyModel
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.data.model.updateEmail.UpdateEmail
import com.example.passerby.data.model.updateName.UpdateName
import com.example.passerby.data.model.userProfile.GetUserProfile
import com.example.passerby.ui.activities.home.fragments.privacyPolicy.PrivacyPolicy
import com.example.passerby.utils.Constants.HOME_TYPE
import com.example.passerby.utils.Constants.LoginSignup_ENTRY_TYPE
import com.example.passerby.utils.Constants.PRIVACYPOLICY_DATA
import com.example.passerby.utils.Constants.TERMS_CONDITIONS_DATA
import com.example.passerby.utils.Constants.TYPE_EMAIL
import com.example.passerby.utils.Constants.USERPROFILE_DATA


class Setting : Fragment(), HomeContract.SettingView {


    lateinit var presenter: HomeContract.HomePresenter
     var passerbyNoti : String = "0"
     var messageNoti : String = "0"
     var user_name : String = ""
     var user_email : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        initializer(view)
        clickListener(view)

        return view
    }

    fun initializer(view : View)
    {
        presenter = HomePresenterImp(this)
        GlobalHelper.showProgress(activity as AppCompatActivity)
        presenter.getSetting()

        val bundle = arguments
        if (bundle!=null && bundle.containsKey(USERPROFILE_DATA))
        {
            val profileModel =  bundle.getParcelable<GetUserProfile>(USERPROFILE_DATA)

            if (profileModel!=null)
            {
                view.txtview_emailSetting.text = profileModel.body.email
                view.txtview_nameSetting.text = profileModel.body.usersDetail.name
            }


        }
    }


    fun clickListener(view: View) {
        view.back_settings.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

        view.lin_name.setOnClickListener {

            val name = view.txtview_nameSetting.text.toString()

            MaterialDialog.Builder(activity as AppCompatActivity)
                .title(getString(R.string.edit_name))
               // .content("Enter")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveText(getString(R.string.save))
                .negativeText(getString(R.string.cancel))
                .input("","", object : MaterialDialog.InputCallback
                {
                    override fun onInput(dialog: MaterialDialog, input: CharSequence?) {

                        GlobalHelper.showProgress(activity as AppCompatActivity)
                        presenter.updatName(input.toString())
                        user_name = input.toString()

                    }

                }).show()
        }



        view.lin_email.setOnClickListener {

            val name = view.txtview_emailSetting.text.toString()

            MaterialDialog.Builder(activity as AppCompatActivity)
                .title(getString(R.string.edit_email))
                // .content("Enter")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveText(getString(R.string.save))
                .negativeText(getString(R.string.cancel))
                .input("","", object : MaterialDialog.InputCallback
                {
                    override fun onInput(dialog: MaterialDialog, input: CharSequence?) {

                        GlobalHelper.showProgress(activity as AppCompatActivity)
                        user_email = input.toString()
                        presenter.updatEmail(user_email)

                    }

                }).show()


        }

        view.rel_toggleLeftNoti.setOnClickListener {

            if(!view.img_toggleLeftNoti.isVisible)
            {
              //  GlobalHelper.showProgress(activity as AppCompatActivity)
                presenter.updateSetting("0", messageNoti)
                passerbyNoti = "0"
            }

            view.img_toggleLeftNoti.visibility = View.VISIBLE
            view.img_toggleRightNoti.visibility = View.GONE
            view.rel_newPasserby.setBackgroundResource(R.drawable.rounded_grey)




        }

        view.rel_toggleRightNoti.setOnClickListener {

            if (!img_toggleRightNoti.isVisible)
            {
             //   GlobalHelper.showProgress(activity as AppCompatActivity)
                presenter.updateSetting("1", messageNoti)
                passerbyNoti = "1"
            }

            view.img_toggleLeftNoti.visibility = View.GONE
            view.img_toggleRightNoti.visibility = View.VISIBLE
            view.rel_newPasserby.setBackgroundResource(R.drawable.rounded_full_themecolored)



        }


        view.rel_toggleLeftNoti_msg.setOnClickListener {

            if (!view.img_toggleLeftNoti_msg.isVisible)
            {
               // GlobalHelper.showProgress(activity as AppCompatActivity)
                presenter.updateSetting(passerbyNoti, "0")
                messageNoti = "0"
            }

            view.img_toggleLeftNoti_msg.visibility = View.VISIBLE
            view.img_toggleRightNoti_msg.visibility = View.GONE
            view.rel_newMessage.setBackgroundResource(R.drawable.rounded_grey)




        }

        view.rel_toggleRightNoti_msg.setOnClickListener {

            if(!view.img_toggleRightNoti_msg.isVisible)
            {
            //    GlobalHelper.showProgress(activity as AppCompatActivity)
                presenter.updateSetting(passerbyNoti, "1")
                messageNoti = "1"
            }

            view.img_toggleLeftNoti_msg.visibility = View.GONE
            view.img_toggleRightNoti_msg.visibility = View.VISIBLE
            view.rel_newMessage.setBackgroundResource(R.drawable.rounded_full_themecolored)

        }


        view.logout_setting.setOnClickListener {
            GlobalHelper.showProgress(activity as AppCompatActivity)
            presenter.logout()

        }

        view.txt_privacyPolicySetting.setOnClickListener {
            GlobalHelper.showProgress(activity as AppCompatActivity)
            presenter.privacyPolicy()
        }

        view.txtview_termsServiceSetting.setOnClickListener {
            GlobalHelper.showProgress(activity as AppCompatActivity)
            presenter.termsConditons()
        }

    }


    override fun onUpdateSettingViewSuccess(updatesetting: UpdateSetting) {
      //  GlobalHelper.hideProgress()
     //   updatesetting.message.let { GlobalHelper.snackBar(view!!.rootSetting, it) }

    }

    override fun onUpdateNameViewSuccess(updateName: UpdateName) {

        GlobalHelper.hideProgress()
        updateName.message.let { GlobalHelper.snackBar(view!!.rootSetting, it) }
        view?.let { it.txtview_nameSetting.text = user_name }
    }

    override fun onUpdateEmailViewSuccess(updateEmail: UpdateEmail) {
        GlobalHelper.hideProgress()
        updateEmail.message.let { GlobalHelper.snackBar(view!!.rootSetting, it) }
       // view?.let { it.txtview_emailSetting .text = user_email }

        val intent = Intent(activity,LoginOrSignup::class.java)
        intent.putExtra(TYPE_EMAIL,user_email)
        intent.putExtra(LoginSignup_ENTRY_TYPE,HOME_TYPE)
        startActivity(intent)
        activity?.let { it.finish() }
    }



    override fun onGetSettingViewSuccess(getsetting: GetSetting) {

        GlobalHelper.hideProgress()
      //  getsetting?.message.let { GlobalHelper.snackBar(view!!.rootSetting, it) }

        passerbyNoti = getsetting.body.passerByNotification.toString()
        messageNoti = getsetting.body.messageNotification.toString()


        if (passerbyNoti.equals("0"))
        {
            img_toggleLeftNoti.visibility = View.VISIBLE
            img_toggleRightNoti.visibility = View.GONE
            rel_newPasserby.setBackgroundResource(R.drawable.rounded_grey)
        }
        else if (passerbyNoti.equals("1"))
        {
            img_toggleLeftNoti.visibility = View.GONE
            img_toggleRightNoti.visibility = View.VISIBLE
            rel_newPasserby.setBackgroundResource(R.drawable.rounded_full_themecolored)
        }

        if (messageNoti.equals("0"))
        {
            img_toggleLeftNoti_msg.visibility = View.VISIBLE
            img_toggleRightNoti_msg.visibility = View.GONE
            rel_newMessage.setBackgroundResource(R.drawable.rounded_grey)

        }
        else if (messageNoti.equals("1"))
        {
            img_toggleLeftNoti_msg.visibility = View.GONE
            img_toggleRightNoti_msg.visibility = View.VISIBLE
            rel_newMessage.setBackgroundResource(R.drawable.rounded_full_themecolored)

        }
    }


    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { view?.rootSetting?.let { it1 -> GlobalHelper.snackBar(it1, it) } }


        //--------------to logout on token expired
        if (error.equals(getString(R.string.user_is_unauthorized)))
        {
            SharedPrefUtil.getInstance()?.clear()
            SharedPrefUtil.getInstance()?.setLogin(false)

            val intent = Intent(activity, LoginOrSignup::class.java)
            startActivity(intent)
            activity?.finish()
        }


    }

    override fun onPrivacyPolicyViewSucces(privacyPolicyModel: PrivacyPolicyModel) {

        GlobalHelper.hideProgress()

        val bundle = Bundle()
        bundle.putParcelable(PRIVACYPOLICY_DATA,privacyPolicyModel)
        val frag = PrivacyPolicy()
        frag.arguments = bundle

        (activity as AppCompatActivity). supportFragmentManager.beginTransaction().replace(R.id.container_home, frag).addToBackStack(null).commit()

    }

    override fun onTermsConditionViewSucces(termsConditionsModel: TermsConditionsModel) {

        GlobalHelper.hideProgress()

        val bundle = Bundle()
        bundle.putParcelable(TERMS_CONDITIONS_DATA,termsConditionsModel)
        val frag = PrivacyPolicy()
        frag.arguments = bundle

        (activity as AppCompatActivity). supportFragmentManager.beginTransaction().replace(R.id.container_home, frag).addToBackStack(null).commit()

    }



    override fun onLogoutViewSucces(logout: Logout) {

        logout.message.let { GlobalHelper.snackBar(view!!.rootSetting, it) }

        GlobalHelper.hideProgress()
        SharedPrefUtil.getInstance()?.clear()
        SharedPrefUtil.getInstance()?.setLogin(false)

        val intent = Intent(activity, LoginOrSignup::class.java)
        //   intent.putExtra(Constants.Login_ENTRY_TYPE,Constants.LOGIN_TYPE)
        startActivity(intent)
        activity?.finish()
    }





}
