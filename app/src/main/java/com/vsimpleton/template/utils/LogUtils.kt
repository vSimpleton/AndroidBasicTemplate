package com.vsimpleton.template.utils

import android.util.Log
import androidx.viewbinding.BuildConfig

object LogUtils {

    private const val TAG = "youzi"

    fun i(message: String?) {
        if (BuildConfig.DEBUG) Log.i(TAG, message.toString())
    }

    fun e(message: String?) {
        if (BuildConfig.DEBUG) Log.e(TAG, message.toString())
    }

}