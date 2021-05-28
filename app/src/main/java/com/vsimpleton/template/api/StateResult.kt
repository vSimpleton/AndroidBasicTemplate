package com.vsimpleton.template.api

import com.vsimpleton.template.data.bean.ExceptionInfo

sealed class StateResult<out T> {

    data class Success<out T>(val data: T?) : StateResult<T>()

    data class Error(val exceptionInfo: ExceptionInfo) : StateResult<Nothing>()

    data class Loading(val isLoading: Boolean) : StateResult<Nothing>()

}