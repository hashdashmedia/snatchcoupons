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
