package com.app.couponapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.data.model.DrawerResponse
import com.app.couponapp.repository.ApiRepository
import com.app.couponapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
    private val repository: ApiRepository
):ViewModel(){

    private val _mutableCouponData = MutableStateFlow<Resource<List<CouponDataResponseItem>>>(Resource.Empty())
    fun collectCouponData(): StateFlow<Resource<List<CouponDataResponseItem>>> = _mutableCouponData

    private val _mutableDrawerData = MutableStateFlow<Resource<DrawerResponse>>(Resource.Empty())
    fun collectDrawerData(): StateFlow<Resource<DrawerResponse>> = _mutableDrawerData

    init {
        getDrawerResponse()
    }

    fun getCouponsListing(id:Int?=null){
       viewModelScope.launch {
           repository.getCouponsListing(id).flowOn(Dispatchers.IO)
               .catch {e->
                  _mutableCouponData.value=Resource.Error(e.message?:"")
               }.collect{
                   _mutableCouponData.value=Resource.Success(it)
               }
       }
    }

    private fun getDrawerResponse(){
       viewModelScope.launch {
           repository.getDrawerResponse().flowOn(Dispatchers.IO)
               .catch {e->
                   _mutableDrawerData.value=Resource.Error(e.message?:"")
               }.collect{
                   _mutableDrawerData.value=Resource.Success(it)
               }
       }
    }
}