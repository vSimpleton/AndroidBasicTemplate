package com.vsimpleton.template.api

import com.vsimpleton.template.data.bean.BaseResponse
import com.vsimpleton.template.data.bean.ExceptionInfo

class CoroutineRequestDSL<T> {

    var requestBlock: (suspend () -> BaseResponse<T>)? = null
        private set

    var successBlock: ((T?) -> Unit)? = null
        private set

    var errorBlock: ((ExceptionInfo) -> Unit)? = null
        private set

    var loadingBlock: ((Boolean) -> Unit)? = null
        private set

    fun request(requestBlock: suspend () -> BaseResponse<T>): CoroutineRequestDSL<T> {
        this.requestBlock = requestBlock
        return this
    }

    fun success(successBlock: (T?) -> Unit): CoroutineRequestDSL<T> {
        this.successBlock = successBlock
        return this
    }

    fun error(errorBlock: ((ExceptionInfo) -> Unit)): CoroutineRequestDSL<T> {
        this.errorBlock = errorBlock
        return this
    }

    fun loading(loadingBlock: ((Boolean) -> Unit)): CoroutineRequestDSL<T> {
        this.loadingBlock = loadingBlock
        return this
    }

}