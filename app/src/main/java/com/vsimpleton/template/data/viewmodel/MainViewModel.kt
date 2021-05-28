package com.vsimpleton.template.data.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vsimpleton.template.api.StateResult
import com.vsimpleton.template.base.BaseViewModel
import com.vsimpleton.template.data.bean.TestBean
import com.vsimpleton.template.data.repository.MainRepository

/**
 * NAME: vSimpleton
 * DATE: 2021/4/17
 * DESC:
 */

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) : BaseViewModel() {

    private val _articleLiveData by lazy { MutableLiveData<StateResult<TestBean>>() }

    val articleLiveData: LiveData<StateResult<TestBean>>
        get() = _articleLiveData

    fun getArticleList() {
        launch<TestBean> {
            request {
                repository.getTestList()
            }.success {
                _articleLiveData.value = StateResult.Success(it)
            }.error {
                _articleLiveData.value = StateResult.Error(it)
            }.loading {
                _articleLiveData.value = StateResult.Loading(it)
            }
        }
    }

}