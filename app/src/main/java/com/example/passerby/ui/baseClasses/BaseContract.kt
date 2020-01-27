package com.example.passerby.ui.baseClasses

interface BaseContract {

    interface BaseOnCompleteListener {
        fun onFailure(error: String)
    }

    interface BaseView {
        fun onFailure(error: String)
    }
}