package com.example.passerby.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.passerby.ui.baseClasses.App
import com.google.android.material.snackbar.Snackbar

class Extensions {

   fun Context.showToast(context: Context, msg: String, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, msg, duration)
    }

  /*  fun View.snack(message: String, length: Int = Toast.LENGTH_LONG) {
        val snack = Snackbar.make(, message, length)
        snack.show()
    }
*/
}