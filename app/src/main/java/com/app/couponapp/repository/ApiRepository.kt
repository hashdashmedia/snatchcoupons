package com.app.couponapp.repository

import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCouponsListing(id: Int?=null)=flow{
        emit(apiService.getCouponsListing(id))
    }.flowOn(Dispatchers.IO)

    suspend fun getDrawerResponse()=flow{
        emit(apiService.getDrawerResponse())
    }.flowOn(Dispatchers.IO)
}