package com.app.couponapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrawerResponse(

	@field:SerializedName("privacy-url")
	val privacyUrl: String? = null,

	@field:SerializedName("app-link")
	val appLink: String? = null,

	@field:SerializedName("rate-us")
	val rateUs: String? = null,

	@field:SerializedName("tnc-url")
	val tncUrl: String? = null,

	@field:SerializedName("about-url")
	val aboutUrl: String? = null
) : Parcelable
