package com.vsimpleton.template.data.repository

import com.vsimpleton.template.api.ApiService
import com.vsimpleton.template.api.BASE_URL
import com.vsimpleton.template.data.bean.BaseResponse
import com.vsimpleton.template.data.bean.TestBean
import com.vsimpleton.template.utils.createApiFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NAME: vSimpleton
 * DATE: 2021/4/17
 * DESC:
 */

@Singleton
class MainRepository @Inject constructor() {

    private val apiService = createApiFactory<ApiService>(BASE_URL)

    suspend fun getTestList(): BaseResponse<TestBean> {
        return apiService.getTest()
    }

}