package com.example.passerby.ui.activities.home

import com.example.passerby.R
import com.example.passerby.data.model.addBio.AddBioModel
import com.example.passerby.data.model.addImage.AddImage
import com.example.passerby.data.model.addProfile.UserDetail
import com.example.passerby.data.model.deleteImage.DeleteImage
import com.example.passerby.data.model.getSetting.GetSetting
import com.example.passerby.data.model.logout.Logout
import com.example.passerby.data.model.privacyPolicy.PrivacyPolicyModel
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.data.model.updateEmail.UpdateEmail
import com.example.passerby.data.model.updateName.UpdateName
import com.example.passerby.data.model.updateProfileImage.UpdateProfileImage
import com.example.passerby.data.model.updateSetting.UpdateSetting
import com.example.passerby.data.model.userDetail.UserDetailModel
import com.example.passerby.data.model.userProfile.GetUserProfile
import com.example.passerby.ui.baseClasses.App

class HomePresenterImp() : HomeContract.HomePresenter, HomeContract.OnHomeCompleteListener {



    lateinit var interactor: HomeContract.HomeInteractor
    var settingView: HomeContract.SettingView? = null
    var homeView: HomeContract.HomeView? = null
    var userProfileView: HomeContract.UserProfileView? = null
    var addBioView: HomeContract.AddBioView? = null
    var userDetailView: HomeContract.UserDetailView? = null


    constructor(setting_view: HomeContract.SettingView) : this() {
        interactor = HomeInteractorImp()
        settingView = setting_view
    }

    constructor(home_view: HomeContract.HomeView) : this() {
        interactor = HomeInteractorImp()
        homeView = home_view
    }

    constructor(userProfile_view: HomeContract.UserProfileView) : this() {
        interactor = HomeInteractorImp()
        userProfileView = userProfile_view
    }

    constructor(addbio_view: HomeContract.AddBioView) : this() {
        interactor = HomeInteractorImp()
        addBioView = addbio_view
    }

    constructor(userDetail_view: HomeContract.UserDetailView) : this() {
        interactor = HomeInteractorImp()
        userDetailView = userDetail_view
    }


    override fun updateSetting(paserbyNoti: String, messageNoti: String) {
        if (App.hasNetwork()) {
            interactor.updateSetting(paserbyNoti,messageNoti,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }

    }

    override fun getSetting() {
        if (App.hasNetwork()) {
            interactor.getSetting(this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun updatName(name: String) {
        if (App.hasNetwork()) {
            interactor.updatName(name,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }


    override fun updatEmail(email: String) {

        if (App.hasNetwork()) {
            interactor.updateEmail(email,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun userProfile(status : String) {
        if (App.hasNetwork()) {
            interactor.userProfile(status,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }



    override fun privacyPolicy() {
        if (App.hasNetwork()) {
            interactor.privacyPolicy(this)
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

    override fun logout() {
        if (App.hasNetwork()) {
            interactor.logout(this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun updateProfileImage(image: String) {
        if (App.hasNetwork()) {
            interactor.updateProfileImage(image,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun addBio(bio: String) {
        if (App.hasNetwork()) {
            interactor.addBio(bio,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }


    override fun addImage(image: String) {
        if (App.hasNetwork()) {
            interactor.addImage(image,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun deleteImage(deleteImage: String) {
        if (App.hasNetwork()) {
            interactor.deleteImage(deleteImage,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }

    override fun userDetail(id: String) {
        if (App.hasNetwork()) {
            interactor.userDetail(id,this)
        } else {
            onFailure(App.instance!!.getString(R.string.no_internet))
        }
    }


    override fun onUpdateSettingSucces(updateSettings: UpdateSetting?) {
     updateSettings?.let { settingView?.onUpdateSettingViewSuccess(it) }
    }

    override fun onGetSettingSucces(getSettings: GetSetting?) {
      getSettings?.let { settingView?.onGetSettingViewSuccess(it) }
    }


    override fun onUpdateNameSucces(updateName: UpdateName?) {
        updateName?.let { settingView?.onUpdateNameViewSuccess(it) }
    }

    override fun onUpdateEamilSucces(updateEmail: UpdateEmail?) {
        updateEmail?.let { settingView?.onUpdateEmailViewSuccess(it) }
    }

    override fun onUserProfileSucces(userProfile: GetUserProfile?) {
        userProfile?.let { homeView?.onUserProfileViewSucces(it) }
    }

    override fun onUserProfileFetchSucces(userProfile: GetUserProfile?) {
        userProfile?.let { userProfileView?.onUserProfileViewSucces(it) }
    }


    override fun onPrivacyPolicySucces(privacyPolicyModel: PrivacyPolicyModel) {
        privacyPolicyModel?.let { settingView?.onPrivacyPolicyViewSucces(it) }
    }

    override fun onTermsConditionSucces(termsConditions: TermsConditionsModel) {
        termsConditions?.let { settingView?.onTermsConditionViewSucces(it) }
    }

    override fun onLogoutProfileSucces(logout: Logout) {
        logout?.let { settingView?.onLogoutViewSucces(it) }
    }

    override fun onUpdateProfileImageSucces(updateProfileImage: UpdateProfileImage) {
        updateProfileImage?.let { userProfileView?.onUpdateProfileImageView(it) }
    }

    override fun addBioSucces(addBio: AddBioModel) {
        addBio?.let { addBioView?.onAddBioView(it) }    }

    override fun addImageSucces(addImage: AddImage) {
        addImage?.let { userProfileView?.onAddImageView(it) }    }

    override fun deleteImageSucces(delImage: DeleteImage) {
        delImage?.let { userProfileView?.onDeleteImageSucces(it) }     }

    override fun onUserDetailSucces(userDetail: UserDetailModel) {
        userDetail?.let { userDetailView?.onUserDetailView(it) }       }





    override fun onFailure(error: String) {

        if(settingView!=null)
            settingView?.onFailure(error)
         else if(homeView!=null)
            homeView?.onFailure(error)
        else if(userProfileView!=null)
            userProfileView?.onFailure(error)
        else if(addBioView!=null)
            addBioView?.onFailure(error)
        else if(userDetailView!=null)
            userDetailView?.onFailure(error)



    }
}
