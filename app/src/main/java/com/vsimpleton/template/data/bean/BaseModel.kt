package com.vsimpleton.template.data.bean

data class BaseModel<T>(
    val code: Int,
    val msg: String,
    val data: T
)
