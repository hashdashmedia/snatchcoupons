package com.app.couponapp.data.remote

import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.data.model.DrawerResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://snatchcoupons.com/wp-json/wp/v2/coupon?coupon-store=48
    @GET("wp-json/wp/v2/coupon")
    suspend fun getCouponsListing(
        @Query("coupon-store") countryCode: Int?=null,
    ):List<CouponDataResponseItem>

    @GET("wp-json/wp/v2/coupon-store/48")
    suspend fun getDrawerResponse():DrawerResponse

}