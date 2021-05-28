package com.vsimpleton.template.data.bean

data class BaseResponse<T>(
    var code: Int = 0,
    var msg: String = "",
    var data: T? = null
)
