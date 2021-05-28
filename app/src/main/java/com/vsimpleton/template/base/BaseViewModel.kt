package com.vsimpleton.template.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsimpleton.template.api.CoroutineRequestDSL
import com.vsimpleton.template.data.bean.BaseResponse
import com.vsimpleton.template.data.bean.ExceptionInfo
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

open class BaseViewModel : ViewModel() {

    private val _errorLiveData = MutableLiveData<ExceptionInfo>()

    private val _loadingLiveData = MutableLiveData<Boolean>()

    val errorLiveData: LiveData<ExceptionInfo>
        get() = _errorLiveData

    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    fun <T> launch(block: CoroutineRequestDSL<T>.() -> Unit): Job {
        CoroutineRequestDSL<T>().apply {
            block.invoke(this)

            return viewModelScope.launch {
                requestBlock?.let { request ->
                    try {
                        loadingBlock?.invoke(true)

                        val baseResponse = request.invoke()
                        if (baseResponse.code == 0) {
                            successBlock?.invoke(baseResponse.data)
                        } else {
                            val exceptionInfo = ExceptionInfo(baseResponse.code, baseResponse.msg)
                            errorBlock?.invoke(exceptionInfo) ?: let {
                                _errorLiveData.value = exceptionInfo
                            }
                        }
                    } catch (e: Exception) {
                        val baseResponse = BaseResponse<T>()
                        when(e) {
                            is HttpException -> {
                                baseResponse.code = e.code()
                                baseResponse.msg = "网络连接出错，请检查网络设置"
                            }

                            else -> {
                                baseResponse.code = -2
                                baseResponse.msg = "未知错误"
                            }
                        }

                        val exceptionInfo = ExceptionInfo(baseResponse.code, baseResponse.msg)
                        errorBlock?.invoke(exceptionInfo) ?: let {
                            _errorLiveData.value = exceptionInfo
                        }

                    } finally {
                        loadingBlock?.invoke(false)
                    }
                }
            }
        }
    }

}