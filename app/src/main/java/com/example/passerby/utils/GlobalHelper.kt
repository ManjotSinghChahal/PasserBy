package com.example.passerby.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.format.DateUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.passerby.R
import com.google.android.material.snackbar.Snackbar
import android.util.DisplayMetrics
import android.util.Log
import java.sql.Timestamp
import java.time.Instant


object GlobalHelper {

    val BASE_URL = "http://18.215.217.31:3000/api/"
    val BASE_URL_IMAGE = "http://18.215.217.31:3000/"
     var mProgress: ProgressDialog? = null

    fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int { // For example columnWidthdp=180
        val displayMetrics = context.getResources().getDisplayMetrics()
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }


    @SuppressLint("WrongConstant")
    fun snackBar(view : View, message: String, duration: Int = Toast.LENGTH_SHORT) {
        val snack = Snackbar.make(view, message, duration).show()

    }

    fun showProgress(mContext: Context) {
        try {
            if (mProgress == null) {
                mProgress = ProgressDialog(mContext)
                mProgress!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            mProgress!!.show()
            mProgress!!.setContentView(R.layout.dialog_progress)
            mProgress!!.setCancelable(false)
        } catch (e: Exception) {
            e.printStackTrace()
            mProgress = null
        }

    }

    // hide the common progress which is used in all application.
    fun hideProgress() {
        try {
            if (mProgress != null) {
                mProgress!!.hide()
                mProgress!!.dismiss()
                mProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mProgress = null
        }

    }

     fun showKeyBoard(view: View, context : Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

     fun hideKeyboard(view: View, context : Context) {
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun getTimeAgo(mReferenceTime: Long): String {

      //  val now = System.currentTimeMillis()
     //   val now = Timestamp(System.currentTimeMillis()).getTime()/1000
        val now = System.currentTimeMillis()
        val diff = now - mReferenceTime*1000
        Log.e("xxxxxx",now.toString())
        Log.e("xxxxxx---->>>",diff.toString())

        if (diff < android.text.format.DateUtils.WEEK_IN_MILLIS) {
            return if (diff <= 1000)
                "Just now"
            else
                android.text.format.DateUtils.getRelativeTimeSpanString(
                    mReferenceTime, now, DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
                ).toString()
        } else if (diff <= 4 * android.text.format.DateUtils.WEEK_IN_MILLIS) {
            val week = (diff / android.text.format.DateUtils.WEEK_IN_MILLIS).toInt()
            return if (week > 1) "$week W" else "$week W"
        } else if (diff < android.text.format.DateUtils.YEAR_IN_MILLIS) {
            val month = (diff / (4 * android.text.format.DateUtils.WEEK_IN_MILLIS)).toInt()
            return if (month > 1) "$month M" else "$month M"
        } else {
            val year = (diff / DateUtils.YEAR_IN_MILLIS).toInt()
            return if (year > 1) "$year Y" else "$year Y"
        }
    }

}