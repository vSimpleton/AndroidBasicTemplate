package com.vsimpleton.template.utils

import android.util.Log
import com.vsimpleton.template.BuildConfig

const val TAG = "youzi"

fun printDebugLog(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.d(TAG, msg)
    }
}
