package com.example.passerby.ui.activities.loginOrSignup.fragments.login

import android.util.Log
import com.example.passerby.R
import com.example.passerby.data.injectorApi.InjectorApi
import com.example.passerby.data.injectorApi.InterfaceApi
import com.example.passerby.data.model.addProfile.AddProfile
import com.example.passerby.data.model.forgotPassword.ForgotPassword
import com.example.passerby.data.model.loginModel.LoginModel
import com.example.passerby.data.model.otpModel.OtpModel
import com.example.passerby.data.model.registerEmailModel.RegisterEmailModel
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.ui.baseClasses.App
import com.example.passerby.utils.Constants
import com.example.passerby.utils.Constants.MESSAGE
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

class LoginInteractorImp : LoginContract.LoginInteractor
{



    lateinit var mApi: InterfaceApi
    init {
        this.mApi = InjectorApi.provideApi()
    }
    override fun login(email: String, password: String, callback: LoginContract.OnLoginCompleteListener) {



        mApi.login(email,password,Constants.DEVICE_TOKEN,Constants.DEVICE_LAT,Constants.DEVICE_LNG).enqueue(object : retrofit2.Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {

               if(response.isSuccessful)
                   callback.onLoginSucces(response.body())
                else
               {
                   try {
                       val body = response.errorBody()!!.string()

                       val `object` = JSONObject(body)
                       val error = `object`.getString(MESSAGE)
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

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                callback.onFailure(t.message.toString())



            }
        })


    }


    override fun email_register(email: String, callback: LoginContract.OnLoginCompleteListener) {

        mApi.email_register(email,Constants.DEVICE_TYPE,Constants.DEVICE_TOKEN).enqueue(object : retrofit2.Callback<RegisterEmailModel> {
            override fun onResponse(call: Call<RegisterEmailModel>, response: Response<RegisterEmailModel>) {

                if(response.isSuccessful)
                    callback.onEmailRegisterSucces(response.body())
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                        val `object` = JSONObject(body)
                        val error = `object`.getString(MESSAGE)
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

            override fun onFailure(call: Call<RegisterEmailModel>, t: Throwable) {
                callback.onFailure(t.message.toString())



            }
        })


    }


    override fun otp(email : String , otp: String, callback: LoginContract.OnLoginCompleteListener) {


        mApi.otp(email,otp).enqueue(object : retrofit2.Callback<OtpModel> {
            override fun onResponse(call: Call<OtpModel>, response: Response<OtpModel>) {


                if(response.isSuccessful)
                    callback.onOtpSucces(response.body())
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                        val `object` = JSONObject(body)
                        val error = `object`.getString(MESSAGE)
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

            override fun onFailure(call: Call<OtpModel>, t: Throwable) {
                callback.onFailure(t.message.toString())
                           }
        })
    }


    override fun addProfile(email: String, password: String, name: String, dob:  String, image: String, latitude: String,
                            longitude: String, location: String, callback: LoginContract.OnLoginCompleteListener)
    {
        var imagePartBody: MultipartBody.Part? = null
      //  val filwe = File("12345.jpg")
        val filwe = File(image)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), filwe)
        imagePartBody = MultipartBody.Part.createFormData("image", image, requestBody)

        val emailBody = RequestBody.create(MediaType.parse("text/plain"), email)
        val passwordBody = RequestBody.create(MediaType.parse("text/plain"), password)
        val nameBody = RequestBody.create(MediaType.parse("text/plain"), name)
        val dobBody = RequestBody.create(MediaType.parse("text/plain"), dob)
        val latitudeBody = RequestBody.create(MediaType.parse("text/plain"), latitude)
        val longitudeBody = RequestBody.create(MediaType.parse("text/plain"), longitude)
        val locationBody = RequestBody.create(MediaType.parse("text/plain"), location)





        mApi.addProfile(emailBody,passwordBody,nameBody,dobBody,latitudeBody,longitudeBody,locationBody,imagePartBody).enqueue(object : retrofit2.Callback<AddProfile>

        {
            override fun onResponse(call: Call<AddProfile>, response: Response<AddProfile>) {


              //  Log.e("fvfvf",response.message())
                if(response.isSuccessful)
                    callback.onaddProfileSucces(response.body())
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                   //     Log.e("vvvvffff",body.toString())
                        val `object` = JSONObject(body)
                        val error = `object`.getString(MESSAGE)
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

            override fun onFailure(call: Call<AddProfile>, t: Throwable) {
                callback.onFailure(t.message.toString())
          //  Log.e("vvvvffff",t.message)
            }



        })
    }

    override fun forgotPassword(email: String, callback: LoginContract.OnLoginCompleteListener)
    {


        mApi.forgotPassword(email).enqueue(object : retrofit2.Callback<ForgotPassword> {
            override fun onResponse(call: Call<ForgotPassword>, response: Response<ForgotPassword>) {


                if(response.isSuccessful)
                    response.body()?.let { callback.onForgotPasswordSucces(it) }
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                        val `object` = JSONObject(body)
                        val error = `object`.getString(MESSAGE)
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

            override fun onFailure(call: Call<ForgotPassword>, t: Throwable) {
                callback.onFailure(t.message.toString())
            }
        })

    }


    override fun termsConditons(callback: LoginContract.OnLoginCompleteListener) {


        val token = "Bearer "+ SharedPrefUtil.getInstance()?.getAuthToken()

        mApi.termsConditions(token).enqueue(object : retrofit2.Callback<TermsConditionsModel>
        {


            override fun onResponse(call: Call<TermsConditionsModel>, response: Response<TermsConditionsModel>) {

                Log.e("rfrfrf",response.message())

                if(response.isSuccessful)
                    response.body()?.let { callback.onTermsConditionSucces(it) }
                else
                {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("rfrfrf",body.toString())
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
                Log.e("rfrfrf",t.message)
            }

        })




    }

}