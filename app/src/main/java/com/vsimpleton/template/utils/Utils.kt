package com.vsimpleton.template.utils

import android.content.res.Resources

object Utils {

    fun dp2px(dpValue: Float): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun px2dp(pxValue: Float): Int {
        return (pxValue / Resources.getSystem().displayMetrics.density).toInt()
    }

}