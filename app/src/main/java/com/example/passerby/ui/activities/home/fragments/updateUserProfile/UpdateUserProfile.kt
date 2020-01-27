package com.example.passerby.ui.activities.home.fragments.updateUserProfile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.passerby.R
import com.example.passerby.data.model.addImage.AddImage
import com.example.passerby.data.model.deleteImage.DeleteImage
import com.example.passerby.data.model.updateProfileImage.UpdateProfileImage
import com.example.passerby.data.model.userProfile.GetUserProfile
import com.example.passerby.ui.activities.home.HomeContract
import com.example.passerby.ui.activities.home.HomePresenterImp
import com.example.passerby.ui.activities.home.fragments.addBio.AddBio
import com.example.passerby.ui.activities.home.fragments.userProfile.UserPhotoAdapter
import com.example.passerby.utils.Constants
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.ImagepickerFragment
import kotlinx.android.synthetic.main.fragment_update_user_profile.view.*


class UpdateUserProfile : ImagepickerFragment(),HomeContract.UserProfileView , UserPhotoAdapter.onDeleteIcon {



    var SelectedProfileImage: Boolean = false
    var profileModel: GetUserProfile? = null
    lateinit var presenter: HomeContract.HomePresenter
    var userAdpt : UserPhotoAdapter? = null
    var totalPassers : String = "0"

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_update_user_profile, container, false)

        view.img_profileImg_userProfileAdd.getLayoutParams().height = (Constants.SCREEN_HEIGHT_PX * 0.65).toInt()

        val bundle = arguments
        if (bundle!=null && bundle.containsKey(Constants.USERPROFILE_DATA))
        {
            profileModel = bundle.getParcelable(Constants.USERPROFILE_DATA)
             totalPassers  = bundle.getString("total_passed")

            Glide.with(this).load("${GlobalHelper.BASE_URL_IMAGE}${profileModel!!.body.usersDetail.profileImage}")
                .into(view.img_profileImg_userProfileAdd)

            view.txt_totalPassed_userProfileAdd.text = totalPassers
            view.username_userProfileAdd.text = profileModel!!.body.usersDetail.name

            if (!profileModel!!.body.usersDetail.bio.isEmpty())
              view.txtview_bioAdd.text = profileModel!!.body.usersDetail.bio


            view.recyclerview_userProfilePicsAdd?.layoutManager = GridLayoutManager(activity, 3) as RecyclerView.LayoutManager?
            if (profileModel!=null)
            {
                userAdpt = activity?.let {
                    UserPhotoAdapter(
                        it,
                        Constants.USER_Profile,
                        profileModel!!,
                        this,true
                    )
                }
                view.recyclerview_userProfilePicsAdd?.adapter = userAdpt
            }

        }

        else if (bundle != null && bundle.containsKey(Constants.BIO_DATA_TYPE)) {
            profileModel = bundle.getParcelable(Constants.BIO_DATA_TYPE)
            view.username_userProfileAdd.text = profileModel!!.body.usersDetail.name
            view.txtview_bioAdd.text = profileModel!!.body.usersDetail.bio


            totalPassers  = bundle.getString(Constants.TOTAL_PASSERS)
            view.txt_totalPassed_userProfileAdd.text = totalPassers
            Glide.with(this).load("${GlobalHelper.BASE_URL_IMAGE}${profileModel!!.body.usersDetail.profileImage}")
                .into(view.img_profileImg_userProfileAdd)

            view.recyclerview_userProfilePicsAdd?.layoutManager = GridLayoutManager(activity, 3) as RecyclerView.LayoutManager?
            if (profileModel!=null)
            {
                userAdpt = activity?.let {
                    UserPhotoAdapter(
                        it,
                        Constants.USER_Profile,
                        profileModel!!,
                        this,true
                    )
                }
                view.recyclerview_userProfilePicsAdd?.adapter = userAdpt
            }


        }



        initialize(view)
        clickListener(view)


        return view
    }


    fun initialize(view : View)
    {
        presenter = HomePresenterImp(this)
    }



    fun clickListener(view : View)
    {


        view.done_userProfileAdd.setOnClickListener {
            activity?.let {
                it.onBackPressed()  }
        }

        view.txtview_bioAdd.setOnClickListener {

                val bundle = Bundle()
                bundle.putString(Constants.TOTAL_PASSERS,totalPassers)
                profileModel?.let { bundle.putParcelable(Constants.BIO_DATA_TYPE, it) }


                val frag = AddBio()
                frag.arguments = bundle
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.container_home, frag)
                        .addToBackStack(null).commit()
                }


        }

        view.addmage_userProfileAdd.setOnClickListener {
            getImage(activity, 0)
            SelectedProfileImage = false
        }


        view.rel_uploadProfileImg_userProfileAdd.setOnClickListener {
            getImage(activity, 0)
            SelectedProfileImage = true
        }


        /*view.recyclerview_userProfilePicsAdd?.layoutManager = GridLayoutManager(activity, 3) as RecyclerView.LayoutManager?
        if (profileModel!=null)
        {
            userAdpt = activity?.let {
                UserPhotoAdapter(
                    it,
                    Constants.USER_Profile,
                    profileModel!!,
                    this
                )
            }
            view.recyclerview_userProfilePics?.adapter = userAdpt
        }*/



    }

    override fun selectedImage(imagePath: String?) {

        GlobalHelper.showProgress(activity as AppCompatActivity)

        if (SelectedProfileImage)
        {
            Glide.with(this).load(imagePath).into(view!!.img_profileImg_userProfileAdd)
            imagePath?.let { presenter.updateProfileImage(it) }
        }
        else
            imagePath?.let { presenter.addImage(it) }
    }



    override fun onUpdateProfileImageView(updateProfileImage: UpdateProfileImage) {
        GlobalHelper.hideProgress()
        updateProfileImage.message.let { GlobalHelper.snackBar(view!!.root_userProfileAdd, it) }
    }

    override fun onAddImageView(addImage: AddImage) {
        GlobalHelper.hideProgress()
        presenter.userProfile(Constants.PROFILE)
        addImage.message.let { GlobalHelper.snackBar(view!!.root_userProfileAdd, it) }
    }

    override fun onUserProfileViewSucces(userProfile: GetUserProfile) {
    }

    override fun onDeleteImageSucces(delImage: DeleteImage) {
        GlobalHelper.hideProgress()
        presenter.userProfile(Constants.PROFILE)
        delImage.message.let { GlobalHelper.snackBar(view!!.root_userProfileAdd, it) }    }


    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.root_userProfileAdd, it) }
    }

    override fun onDeleteSuccess(value: String) {
        GlobalHelper.showProgress(activity as AppCompatActivity)
        presenter.deleteImage(value)
    }


}
