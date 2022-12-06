package com.app.couponapp.repository

import com.app.couponapp.data.remote.ApiService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val newsApi: ApiService
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<ResponseBody> {
        //return newsApi.getBreakingNews(countryCode,pageNumber)
        return Response()
    }

}