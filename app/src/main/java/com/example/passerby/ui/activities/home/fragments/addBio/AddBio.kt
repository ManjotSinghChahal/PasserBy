package com.example.passerby.ui.activities.home.fragments.addBio


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.passerby.R
import com.example.passerby.data.model.addBio.AddBioModel
import com.example.passerby.data.model.userProfile.GetUserProfile
import com.example.passerby.ui.activities.home.HomeContract
import com.example.passerby.ui.activities.home.HomePresenterImp
import com.example.passerby.ui.activities.home.fragments.updateUserProfile.UpdateUserProfile
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.BIO_DATA_TYPE
import com.example.passerby.utils.GlobalHelper
import kotlinx.android.synthetic.main.fragment_add_bio.view.*


class AddBio : Fragment() , HomeContract.AddBioView{



    lateinit var profileModel : GetUserProfile
    lateinit var presenter: HomeContract.HomePresenter
    var total_passers = "0"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_bio, container, false)


        clickListener(view)

        presenter = HomePresenterImp(this)


        val bundle = arguments

        if (bundle!=null && bundle.containsKey(BIO_DATA_TYPE))
        {
            profileModel = bundle.getParcelable(BIO_DATA_TYPE)
            total_passers = bundle.getString(Constants.TOTAL_PASSERS)

            view.edt_addBio.setText(profileModel.body.usersDetail.bio)
        }


        return view
    }


      fun clickListener(view : View)
      {


          view.back_addBio.setOnClickListener {
              activity?.let { it.onBackPressed() }
          }

          view.txt_add_addBio.setOnClickListener {


              presenter.addBio(view.edt_addBio.text.toString().trim())
              GlobalHelper.showProgress(activity as AppCompatActivity)





          }
      }

    override fun onAddBioView(addBio: AddBioModel) {

        GlobalHelper.hideProgress()
        addBio.message.let { GlobalHelper.snackBar(view!!.rootAddBio, it) }

        val bundle = arguments
        if (bundle!=null && bundle.containsKey(BIO_DATA_TYPE))
        {
            profileModel.body.usersDetail.bio = view?.edt_addBio?.text.toString()
            bundle.putParcelable(BIO_DATA_TYPE,profileModel)
            bundle.putString(Constants.TOTAL_PASSERS,total_passers)
            val frag = UpdateUserProfile()
            fragmentManager?.popBackStack()
            fragmentManager?.popBackStack()
            frag.arguments = bundle
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container_home,frag)
                    .addToBackStack(null).commit()
            }
        }


    }

    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.rootAddBio, it) }
    }



}
