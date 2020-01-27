package com.example.passerby.ui.activities.home

import android.util.Log
import com.example.passerby.R
import com.example.passerby.data.injectorApi.InjectorApi
import com.example.passerby.data.injectorApi.InterfaceApi
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
import com.example.passerby.utils.Constants
import com.example.passerby.utils.SharedPrefUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.IOException

class HomeInteractorImp : HomeContract.HomeInteractor
{



    lateinit var mApi: InterfaceApi
    lateinit var auth_token: String
    init {
        this.mApi = InjectorApi.provideApi()
        auth_token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()
    }

    override fun updateSetting(paserbyNoti: String, messageNoti: String, callback: HomeContract.OnHomeCompleteListener)
    {



        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()
        mApi.updateSetting(token,paserbyNoti,messageNoti).enqueue(object : retrofit2.Callback<UpdateSetting>
        {

            override fun onResponse(call: Call<UpdateSetting>, response: Response<UpdateSetting>) {

                if(response.isSuccessful)
                    callback.onUpdateSettingSucces(response.body())
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<UpdateSetting>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })
    }

    override fun getSetting(callback: HomeContract.OnHomeCompleteListener) {

        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.getSetting(token).enqueue(object : retrofit2.Callback<GetSetting>
        {

            override fun onResponse(call: Call<GetSetting>, response: Response<GetSetting>) {

                if(response.isSuccessful)
                    callback.onGetSettingSucces(response.body())
                else
                {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<GetSetting>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })


    }



    override fun updatName(name: String, callback: HomeContract.OnHomeCompleteListener) {

        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.updateName(token,name).enqueue(object : retrofit2.Callback<UpdateName>
        {

            override fun onResponse(call: Call<UpdateName>, response: Response<UpdateName>) {

                if(response.isSuccessful)
                    callback.onUpdateNameSucces(response.body())
                else
                {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<UpdateName>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })

    }

    override fun updateEmail(email: String, callback: HomeContract.OnHomeCompleteListener)
    {

        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.updateEmail(token,email).enqueue(object : retrofit2.Callback<UpdateEmail>
        {

            override fun onResponse(call: Call<UpdateEmail>, response: Response<UpdateEmail>) {

                if(response.isSuccessful)
                    callback.onUpdateEamilSucces(response.body())
                else
                {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<UpdateEmail>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })


    }

    override fun userProfile(status : String , callback: HomeContract.OnHomeCompleteListener) {

        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.getMyProfile(token).enqueue(object : retrofit2.Callback<GetUserProfile>
        {

            override fun onResponse(call: Call<GetUserProfile>, response: Response<GetUserProfile>) {



                if(response.isSuccessful)
                {
                    if (status.equals(Constants.HOME))
                        callback.onUserProfileSucces(response.body())
                    else if (status.equals(Constants.PROFILE))
                        callback.onUserProfileFetchSucces(response.body())
                }

                else
                {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<GetUserProfile>, t: Throwable) {
                callback.onFailure(t.message.toString())

            }

        })

    }

    override fun privacyPolicy(callback: HomeContract.OnHomeCompleteListener) {

        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.privacyPolicy(token).enqueue(object : retrofit2.Callback<PrivacyPolicyModel>
        {

            override fun onResponse(call: Call<PrivacyPolicyModel>, response: Response<PrivacyPolicyModel>) {

                if(response.isSuccessful)
                    response.body()?.let { callback.onPrivacyPolicySucces(it) }
                else
                {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<PrivacyPolicyModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })



    }


    override fun termsConditons(callback: HomeContract.OnHomeCompleteListener) {


        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

             mApi.termsConditions(token).enqueue(object : retrofit2.Callback<TermsConditionsModel>
             {


                 override fun onResponse(call: Call<TermsConditionsModel>, response: Response<TermsConditionsModel>) {

                     if(response.isSuccessful)
                         response.body()?.let { callback.onTermsConditionSucces(it) }
                     else
                     {
                         try {
                             val body = response.errorBody()!!.string()

                             val `object` = JSONObject(body)
                             val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                             callback.onFailure(error)
                         } catch (e: JSONException) {
                             App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                             e.printStackTrace()
                         } catch (e: IOException) {
                             App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                             e.printStackTrace()
                         }

                     }


                 }

                 override fun onFailure(call: Call<TermsConditionsModel>, t: Throwable) {
                     callback.onFailure(t.message.toString())
                 }

             })



    }

    override fun logout(callback: HomeContract.OnHomeCompleteListener) {


        val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.logout(token).enqueue(object : retrofit2.Callback<Logout>
        {

            override fun onResponse(call: Call<Logout>, response: Response<Logout>) {

                if(response.isSuccessful)
                    response.body()?.let { callback.onLogoutProfileSucces(it) }
                else
                {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<Logout>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })

    }


    override fun updateProfileImage(image: String, callback: HomeContract.OnHomeCompleteListener) {

       // val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()


        var imagePartBody: MultipartBody.Part? = null
        val file = File(image)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        imagePartBody = MultipartBody.Part.createFormData("image", image, requestBody)

      //  val tokenBody = RequestBody.create(MediaType.parse("text/plain"), token)



        mApi.updateProfileImage(auth_token,imagePartBody).enqueue(object : retrofit2.Callback<UpdateProfileImage> {

            override fun onResponse(call: Call<UpdateProfileImage>, response: Response<UpdateProfileImage>) {

                if (response.isSuccessful)
                    response.body()?.let { callback.onUpdateProfileImageSucces(it) }
                else {
                    try {
                        val body = response.errorBody()!!.string()
                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<UpdateProfileImage>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }
        })
    }


    override fun addBio(bio: String, callback: HomeContract.OnHomeCompleteListener) {


     //   val token = "Bearer "+SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.addBio(auth_token,bio).enqueue(object : retrofit2.Callback<AddBioModel>
        {

            override fun onResponse(call: Call<AddBioModel>, response: Response<AddBioModel>) {


                if(response.isSuccessful)
                    response.body()?.let { callback.addBioSucces(it) }
                else
                {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<AddBioModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })

    }

    override fun addImage(image: String, callback: HomeContract.OnHomeCompleteListener) {


        var imagePartBody: MultipartBody.Part? = null
        val file = File(image)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        imagePartBody = MultipartBody.Part.createFormData("image", image, requestBody)

        //  val tokenBody = RequestBody.create(MediaType.parse("text/plain"), token)



        mApi.addImage(auth_token,imagePartBody).enqueue(object : retrofit2.Callback<AddImage> {

            override fun onResponse(call: Call<AddImage>, response: Response<AddImage>) {


                if (response.isSuccessful)
                    response.body()?.let { callback.addImageSucces(it) }
                else {
                    try {
                        val body = response.errorBody()!!.string()

                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<AddImage>, t: Throwable) {
                callback.onFailure(t.message.toString())

            }
        })

    }


    override fun deleteImage(deleteImage: String, callback: HomeContract.OnHomeCompleteListener) {

        mApi.deleteImage(auth_token,deleteImage).enqueue(object : retrofit2.Callback<DeleteImage>
        {

            override fun onResponse(call: Call<DeleteImage>, response: Response<DeleteImage>) {


                if(response.isSuccessful)
                    response.body()?.let { callback.deleteImageSucces(it) }
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                      Log.e("frfrfr",body.toString())
                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<DeleteImage>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })
    }


    override fun userDetail(id: String, callback: HomeContract.OnHomeCompleteListener) {



        mApi.userDetail(auth_token,id).enqueue(object : retrofit2.Callback<UserDetailModel>
        {

            override fun onResponse(call: Call<UserDetailModel>, response: Response<UserDetailModel>) {


                if(response.isSuccessful)
                    response.body()?.let { callback.onUserDetailSucces(it) }
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                      Log.e("eeeee",body.toString())
                        val `object` = JSONObject(body)
                        val error = `object`.getString(com.example.passerby.utils.Constants.MESSAGE)
                        callback.onFailure(error)
                    } catch (e: JSONException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    } catch (e: IOException) {
                        App.instance?.getString(R.string.some_error)?.let { callback.onFailure(it) }
                        e.printStackTrace()
                    }

                }


            }

            override fun onFailure(call: Call<UserDetailModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }

        })
    }

}