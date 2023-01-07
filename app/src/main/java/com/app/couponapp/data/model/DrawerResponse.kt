package com.app.couponapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrawerResponse(
	@field:SerializedName("acf")
	val data: Data? = null,
) : Parcelable

@Parcelize
data class Data(
	@field:SerializedName("deal_detail_banner")
	val dealDetailBanner: String? = null,

	@field:SerializedName("deal_detail_interstitial")
	val dealDetailInterstitial: String? = null,

	@field:SerializedName("coupon_detail_banner")
	val couponDetailBanner: String? = null,

	@field:SerializedName("coupon_detail_interstitial")
	val couponDetailInterstitial: String? = null,

	@field:SerializedName("coupon_ad_banner")
	val couponAdBanner: String? = null,

	@field:SerializedName("coupon_ad_interstitial")
	val couponAdInterstitial: String? = null,

	@field:SerializedName("home_ad_banner")
	val homeAdBanner: String? = null,

	@field:SerializedName("home_ad_interstitial")
	val homeAdInterstitial: String? = null,

	@field:SerializedName("store_image_url")
	val storeImageUrl: String? = null,

	@field:SerializedName("privacy_policy")
	val privacy_policy: String? = null,

	@field:SerializedName("app-link")
	val appLink: String? = null,

	@field:SerializedName("rate-us")
	val rateUs: String? = null,

	@field:SerializedName("terms_and_conditions")
	val tncUrl: String? = null,

	@field:SerializedName("about_us")
	val aboutUrl: String? = null
) : Parcelable
