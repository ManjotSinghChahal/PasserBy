package com.example.passerby.data.injectorApi

import com.example.passerby.data.model.addBio.AddBioModel
import com.example.passerby.data.model.addImage.AddImage
import com.example.passerby.data.model.addProfile.AddProfile
import com.example.passerby.data.model.addProfile.UserDetail
import com.example.passerby.data.model.deleteImage.DeleteImage
import com.example.passerby.data.model.forgotPassword.ForgotPassword
import com.example.passerby.data.model.getSetting.GetSetting
import com.example.passerby.data.model.loginModel.LoginModel
import com.example.passerby.data.model.logout.Logout
import com.example.passerby.data.model.otpModel.OtpModel
import com.example.passerby.data.model.privacyPolicy.PrivacyPolicyModel
import com.example.passerby.data.model.registerEmailModel.RegisterEmailModel
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.data.model.updateEmail.UpdateEmail
import com.example.passerby.data.model.updateName.UpdateName
import com.example.passerby.data.model.updateProfileImage.UpdateProfileImage
import com.example.passerby.data.model.updateSetting.UpdateSetting
import com.example.passerby.data.model.userDetail.UserDetailModel
import com.example.passerby.data.model.userProfile.GetUserProfile
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

interface InterfaceApi {


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String, @Field("password") password: String, @Field("deviceToken") deviceToken: String, @Field(
            "latitude"
        ) latitude: String, @Field("longitude") longitude: String
    ): Call<LoginModel>


    @FormUrlEncoded
    @POST("sendotp")
    fun email_register(@Field("email") email: String, @Field("deviceType") deviceType: String, @Field("deviceToken") deviceToken: String): Call<RegisterEmailModel>


    @FormUrlEncoded
    @POST("verifyOtp")
    fun otp(@Field("email") email: String, @Field("otp") otp: String): Call<OtpModel>


    @Multipart
    @POST("updateProfile")
    fun addProfile(
        @Part("email") email: RequestBody, @Part("password") password: RequestBody, @Part("name") name: RequestBody, @Part(
            "dob"
        ) dob: RequestBody, @Part("latitude") latitude: RequestBody, @Part("longitude") longitude: RequestBody, @Part("location") location: RequestBody, @Part image: MultipartBody.Part
    ): Call<AddProfile>


    @Multipart
    @POST("updateProfile")
    fun addProfile2(
        @Part("email") email: RequestBody, @Part("password") password: RequestBody, @Part("name") name: RequestBody, @Part(
            "dob"
        ) dob: RequestBody, @Part("latitude") latitude: RequestBody, @Part("longitude") longitude: RequestBody, @Part("location") location: RequestBody
    ): Call<AddProfile>


    @FormUrlEncoded
    @PUT("UpdateSettings")
    fun updateSetting(
        @Header("Authorization") authHeader: String, @Field("passerByNotification") passerByNotification: String, @Field(
            "messageNotification"
        ) messageNotification: String
    ): Call<UpdateSetting>


    @GET("getSettings")
    fun getSetting(@Header("Authorization") authHeader: String): Call<GetSetting>

    @FormUrlEncoded
    @PUT("UpdateName")
    fun updateName(@Header("Authorization") authHeader: String, @Field("name") name: String): Call<UpdateName>

    @FormUrlEncoded
    @PUT("updateEmail")
    fun updateEmail(@Header("Authorization") authHeader: String, @Field("email") name: String): Call<UpdateEmail>


    @GET("getMyProfile")
    fun getMyProfile(@Header("Authorization") authHeader: String): Call<GetUserProfile>

    @GET("privacyPolicy")
    fun privacyPolicy(@Header("Authorization") authHeader: String): Call<PrivacyPolicyModel>

    @GET("termsCondition")
    fun termsConditions(@Header("Authorization") authHeader: String): Call<TermsConditionsModel>


    @POST("logout")
    fun logout(@Header("Authorization") authHeader: String): Call<Logout>


    @FormUrlEncoded
    @POST("forgetPassword")
    fun forgotPassword(@Field("email") email: String): Call<ForgotPassword>

    @Multipart
    @POST("updateProfileImage")
    fun updateProfileImage(@Header("Authorization") authHeader: String, @Part image: MultipartBody.Part): Call<UpdateProfileImage>


    @FormUrlEncoded
    @PUT("updateBio")
    fun addBio(@Header("Authorization") authHeader: String, @Field("bio") bio: String): Call<AddBioModel>

    @Multipart
    @POST("uploadImage")
    fun addImage(@Header("Authorization") authHeader: String, @Part image: MultipartBody.Part): Call<AddImage>



    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteImage", hasBody = true)
    fun deleteImage(@Header("Authorization") authHeader: String, @Field("imageId") imageId: String): Call<DeleteImage>


    @GET("viewProfile")
    fun userDetail(@Header("Authorization") authHeader: String, @Query("id") bio: String): Call<UserDetailModel>





}