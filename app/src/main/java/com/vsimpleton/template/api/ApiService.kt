package com.vsimpleton.template.api

import com.vsimpleton.template.data.bean.BaseResponse
import com.vsimpleton.template.data.bean.TestBean
import retrofit2.http.GET

interface ApiService {

    @GET(TEST)
    suspend fun getTest(): BaseResponse<TestBean>

}