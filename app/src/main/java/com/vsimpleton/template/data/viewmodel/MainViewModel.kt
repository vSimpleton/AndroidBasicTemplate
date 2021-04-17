package com.vsimpleton.template.data.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.vsimpleton.template.base.BaseViewModel
import com.vsimpleton.template.data.repository.MainRepository
import com.vsimpleton.template.utils.printDebugLog
import kotlinx.coroutines.launch

/**
 * NAME: vSimpleton
 * DATE: 2021/4/17
 * DESC:
 */

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) : BaseViewModel() {

    fun test() {
        viewModelScope.launch {
            repository.getTestList()
        }
    }

}