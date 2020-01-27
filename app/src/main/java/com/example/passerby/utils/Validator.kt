package com.example.passerby.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.example.passerby.R
import com.example.passerby.ui.baseClasses.App

class Validator {



    fun loginValidator(email: String, password: String, root: LinearLayout, context: Context): Boolean {
        if (email.trim().isEmpty()) {
            context.getString(R.string.email_empty)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            context.getString(R.string.email_invalid)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        else if (password.trim ().isEmpty()) {
            context.getString(R.string.password_empty)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        else if (password.trim ().length<6) {
            context.getString(R.string.password_invalid)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }


        return true
    }

    fun emailValidator(email: String, root: View, context: Context): Boolean {
        if (email.trim().isEmpty()) {
            context.getString(R.string.email_empty)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            context.getString(R.string.email_invalid)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        return true
    }

    fun passwordValidator(password: String, root: View, context: Context): Boolean {
        if (password.trim ().isEmpty()) {
            context.getString(R.string.password_empty)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        else if (password.trim ().length<6) {
            context.getString(R.string.password_invalid)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }


        return true
    }
    fun nameValidator(name: String, root: View, context: Context): Boolean {
        if (name.trim ().isEmpty()) {
            context.getString(R.string.name_empty)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        else if (name.trim ().length<3) {
            context.getString(R.string.name_invalid)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        return true
    }

    fun otpValidator(otpLength: Int, root: View, context: Context): Boolean {
        if (otpLength<4) {
            context.getString(R.string.enter_otp_full)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        return true
    }

    fun bdayValidator(bday: String, root: View, context: Context): Boolean {
        if (bday.isEmpty()) {
            context.getString(R.string.select_dob)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        return true
    }



    fun imageValidator(image: String, root: View, context: Context): Boolean {
        if (image.trim ().isEmpty()) {
            context.getString(R.string.image_empty)?.let { GlobalHelper.snackBar(root, it) }
            return false
        }
        return true
    }




    companion object {
        private var sInstance: Validator? = null

        fun getsInstance(): Validator {
            if (sInstance == null) {
                sInstance = Validator()
            }
            return sInstance as Validator
        }
    }
}
