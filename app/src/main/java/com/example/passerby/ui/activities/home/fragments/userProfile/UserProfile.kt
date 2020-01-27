package com.example.passerby.ui.activities.home.fragments.userProfile


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
import com.example.passerby.ui.activities.home.fragments.updateUserProfile.UpdateUserProfile
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.BIO_DATA_TYPE
import com.example.passerby.utils.Constants.PROFILE
import com.example.passerby.utils.Constants.USER_Profile
import com.example.passerby.utils.GlobalHelper
import com.example.passerby.utils.GlobalHelper.BASE_URL_IMAGE
import com.example.passerby.utils.ImagepickerFragment
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import com.example.passerby.ui.baseClasses.App
import com.example.passerby.utils.SocketManager
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


class UserProfile : ImagepickerFragment(), HomeContract.UserProfileView , UserPhotoAdapter.onDeleteIcon , SocketManager.Observer{



    var mSocket: Socket? = null
    var mSocketManager: SocketManager? = null
    lateinit var presenter: HomeContract.HomePresenter
    var SelectedProfileImage: Boolean = false
    var profileModel: GetUserProfile? = null
    var userAdpt : UserPhotoAdapter? = null
    var bio : String = ""
    var totalPassed : String = ""
    var imgPath : String = ""

  //  var recyclerview_userProfilePics : RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        view.img_profileImg_userProfile.getLayoutParams().height = (Constants.SCREEN_HEIGHT_PX * 0.65).toInt()


        initializer(view)
        clickListener(view)



        return view
    }

    fun initializer(view: View) {

        presenter = HomePresenterImp(this)

        val bundle = arguments
        if (bundle != null && bundle.containsKey(Constants.USERPROFILE_DATA)) {
            profileModel = bundle.getParcelable(Constants.USERPROFILE_DATA)

            if (profileModel != null) {
                //  view.txtview_emailSetting.text = profileModel.body.email
                view.username_userProfile.text = profileModel!!.body.usersDetail.name
                view.txtview_bio.text = profileModel!!.body.usersDetail.bio
                bio = profileModel!!.body.usersDetail.bio


                val app = activity?.application as App
                mSocket = app.getSocket()
                mSocketManager = App.instance!!.getSocketManager()
                val jsonObject = JSONObject()
                jsonObject.put(mSocketManager!!.USER_ID, profileModel!!.body.id)
                mSocketManager!!.onTotalPassed(jsonObject)


                Glide.with(this).load("$BASE_URL_IMAGE${profileModel!!.body.usersDetail.profileImage}")
                    .into(view.img_profileImg_userProfile)
              /*  Glide.with(this)
                    .asBitmap()
                    .load("$BASE_URL_IMAGE${profileModel!!.body.usersDetail.profileImage}")
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            view.img_profileImg_userProfile.setImageBitmap(resource)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                        }
                    })*/
            }


        } else if (bundle != null && bundle.containsKey(BIO_DATA_TYPE)) {
            profileModel = bundle.getParcelable(BIO_DATA_TYPE)
            view.username_userProfile.text = profileModel!!.body.usersDetail.name
            view.txtview_bio.text = profileModel!!.body.usersDetail.bio

            Glide.with(this).load("$BASE_URL_IMAGE${profileModel!!.body.usersDetail.profileImage}")
                .into(view.img_profileImg_userProfile)

        }
        else
        {
            GlobalHelper.showProgress(activity as AppCompatActivity)
            presenter.userProfile(PROFILE)
        }


    }


    fun clickListener(view: View) {

      //  if (recyclerview_userProfilePics!=null)
      //  {
        view.recyclerview_userProfilePics?.layoutManager = GridLayoutManager(activity, 3) as RecyclerView.LayoutManager?
      if (profileModel!=null)
      {
          userAdpt = activity?.let {
              UserPhotoAdapter(
                  it,
                  USER_Profile,
                  profileModel!!,
                  this,false
              )
          }
          view.recyclerview_userProfilePics?.adapter = userAdpt
      }
     //   }
        view.done_userProfile.setOnClickListener { activity?.let { it.onBackPressed() } }





        view.img_userProfile_userDetail.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelable(Constants.USERPROFILE_DATA,profileModel)
            bundle.putString("total_passed",totalPassed)
            val frag = UpdateUserProfile()
            frag.arguments = bundle
            activity?.let { it.supportFragmentManager.beginTransaction().replace(R.id.container_home, frag).addToBackStack(null).commit() }

           /* view.img_userProfile_userDetail.visibility = View.GONE
            view.addmage_userProfile.visibility = View.VISIBLE
            view.done_userProfile.visibility = View.VISIBLE
            view.rel_uploadProfileImg_userProfile.visibility = View.VISIBLE
            view.img_back_userProfile.visibility = View.GONE
            view.rel_wave_userProfile.visibility = View.GONE


            if (bio!!.isEmpty())
            {
                view.txtview_bio.text = getString(R.string.add_bio)
            }*/
        }

        view.img_back_userProfile.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

        view.rel_uploadProfileImg_userProfile.setOnClickListener {
            getImage(activity, 0)
            SelectedProfileImage = true
        }

        view.addmage_userProfile.setOnClickListener {
            getImage(activity, 0)
            SelectedProfileImage = false
        }

       /* view.txtview_bio.setOnClickListener {
            if (view.addmage_userProfile.isVisible) {
                val bundle = Bundle()

                profileModel?.let { bundle.putParcelable(BIO_DATA_TYPE, it) }


                val frag = AddBio()
                frag.arguments = bundle
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.container_home, frag)
                        .addToBackStack(null).commit()
                }

            }
        }
*/

    }


    override fun selectedImage(imagePath: String?) {

        GlobalHelper.showProgress(activity as AppCompatActivity)

        if (SelectedProfileImage)
        {
            Glide.with(this).load(imagePath).into(img_profileImg_userProfile)
            imagePath?.let { presenter.updateProfileImage(it) }
        }
        else
            imagePath?.let { presenter.addImage(it) }



    }

    override fun onResume() {
        super.onResume()
        mSocketManager?.onRegister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocketManager?.unRegister(this)
        mSocketManager?.disconnectTotalPassed()
    }


    override fun onUpdateProfileImageView(updateProfileImage: UpdateProfileImage) {
        GlobalHelper.hideProgress()
        updateProfileImage.message.let { GlobalHelper.snackBar(view!!.root_userProfile, it) }


    }

    override fun onFailure(error: String) {
        GlobalHelper.hideProgress()
        error.let { GlobalHelper.snackBar(view!!.root_userProfile, it) }
    }

    override fun onAddImageView(addImage: AddImage) {

        presenter.userProfile(PROFILE)
        addImage.message.let { GlobalHelper.snackBar(view!!.root_userProfile, it) }    }


    override fun onUserProfileViewSucces(userProfile: GetUserProfile) {

            GlobalHelper.hideProgress()
            profileModel = userProfile
     //   userAdpt?.let { it.notifyDataSetChanged() }

            userAdpt = activity?.let {
                UserPhotoAdapter(
                    it,
                    USER_Profile,
                    userProfile!!,
                    this,false
                )
            }
        view?.recyclerview_userProfilePics?.let { it.adapter = userAdpt }


        }

    override fun onDeleteSuccess(value: String) {
        GlobalHelper.showProgress(activity as AppCompatActivity)
        presenter.deleteImage(value)
    }

    override fun onDeleteImageSucces(delImage: DeleteImage) {

        presenter.userProfile(PROFILE)
        delImage.message.let { GlobalHelper.snackBar(view!!.root_userProfile, it) }
    }


    override fun onChatDetail(event: String, args: JSONArray) {
    }

    override fun onChatList(event: String, args: JSONArray) {
    }

    override fun onTotalPassed(event: String, args: JSONObject) {

        try {

            val jsonObject = JSONObject(args.toString())
            val totalPassers : String =  jsonObject.get("users").toString()
            totalPassed = totalPassers
            activity?.let {
               it.runOnUiThread {

                   view?.txt_totalPassed_userProfile?.text = totalPassers.toString()
               }
            }



        }catch (ex : Exception)
        {}

    }
    override fun onNearbyUsersList(event: String, args: JSONArray) {
    }

    override fun onResponseArray(event: String, args: JSONArray) {
    }

    override fun onResponseObject(event: String, args: JSONObject) {
    }

}
