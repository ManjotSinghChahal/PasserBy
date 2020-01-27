package com.example.passerby.ui.activities.home

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
import com.example.passerby.ui.baseClasses.BaseContract

interface HomeContract
{

    interface HomePresenter
    {
        fun updateSetting(paserbyNoti : String,messageNoti : String)
        fun getSetting()
        fun updatName(name : String)
        fun updatEmail(email : String)
        fun userProfile(status : String)
        fun userDetail(id : String)
        fun privacyPolicy()
        fun termsConditons()
        fun logout()
        fun updateProfileImage(image : String)
        fun addBio(bio : String)
        fun addImage(image : String)
        fun deleteImage(deleteImage : String)

    }

    interface HomeInteractor
    {
        fun updateSetting(paserbyNoti : String,messageNoti : String, callback : OnHomeCompleteListener)
        fun getSetting(callback : OnHomeCompleteListener)
        fun updatName(name : String, callback : OnHomeCompleteListener)
        fun updateEmail(email : String, callback : OnHomeCompleteListener)
        fun userProfile(status : String , callback : OnHomeCompleteListener)
        fun userDetail(id : String , callback : OnHomeCompleteListener)
        fun privacyPolicy(callback : OnHomeCompleteListener)
        fun termsConditons(callback : OnHomeCompleteListener)
        fun logout(callback : OnHomeCompleteListener)
        fun updateProfileImage(image : String, callback : OnHomeCompleteListener)
        fun addBio(bio : String, callback : OnHomeCompleteListener)
        fun addImage(image : String, callback : OnHomeCompleteListener)
        fun deleteImage(deleteImage : String, callback : OnHomeCompleteListener)
        }


    interface OnHomeCompleteListener : BaseContract.BaseOnCompleteListener
    {
        fun onUpdateSettingSucces(updateSettings: UpdateSetting?)
        fun onGetSettingSucces(getSettings: GetSetting?)
        fun onUpdateNameSucces(updateName: UpdateName?)
        fun onUpdateEamilSucces(updateEmail: UpdateEmail?)
        fun onUserProfileSucces(userProfile: GetUserProfile?)
        fun onUserProfileFetchSucces(userProfile: GetUserProfile?)
        fun onUserDetailSucces(userDetail: UserDetailModel)
        fun onPrivacyPolicySucces(privacyPolicyModel: PrivacyPolicyModel)
        fun onTermsConditionSucces(termsConditions: TermsConditionsModel)
        fun onLogoutProfileSucces(logout: Logout)
        fun onUpdateProfileImageSucces(updateProfileImage: UpdateProfileImage)
        fun addBioSucces(addBio: AddBioModel)
        fun addImageSucces(addImage: AddImage)
        fun deleteImageSucces(delImage: DeleteImage)



    }

    interface SettingView : BaseContract.BaseView
    {
        fun onUpdateSettingViewSuccess(updatesetting: UpdateSetting)
        fun onGetSettingViewSuccess(getsetting: GetSetting)
        fun onUpdateNameViewSuccess(updateName: UpdateName)
        fun onUpdateEmailViewSuccess(updateEmail: UpdateEmail)
        fun onPrivacyPolicyViewSucces(privacyPolicyModel: PrivacyPolicyModel)
        fun onTermsConditionViewSucces(termsConditions: TermsConditionsModel)
        fun onLogoutViewSucces(logout: Logout)

    }

    interface HomeView : BaseContract.BaseView
    {
        fun onUserProfileViewSucces(userProfile: GetUserProfile?)
    }

    interface UserProfileView : BaseContract.BaseView
    {
        fun  onUpdateProfileImageView(updateProfileImage: UpdateProfileImage)
        fun  onAddImageView(addImage: AddImage)
        fun onUserProfileViewSucces(userProfile: GetUserProfile)
        fun onDeleteImageSucces(delImage: DeleteImage)
    }

    interface AddBioView : BaseContract.BaseView
    {
        fun  onAddBioView(addBio: AddBioModel)
    }

    interface UserDetailView : BaseContract.BaseView
    {
        fun  onUserDetailView(userDetail: UserDetailModel)
    }






}