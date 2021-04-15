package com.vsimpleton.template.utils

object Configure {

    // 是否已经登录
    fun setIsLogin(isLogin: Boolean) {
        DataStoreUtils.putData("isLogin", isLogin)
    }

    fun getIsLogin(): Boolean {
        return DataStoreUtils.getSyncData("isLogin", false)
    }

}