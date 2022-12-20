package com.app.couponapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class CollectionItem(

	@field:SerializedName("href")
	val href: String? = null
) : Parcelable

@Parcelize
data class WpTermItem(

	@field:SerializedName("taxonomy")
	val taxonomy: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("embeddable")
	val embeddable: Boolean? = null
) : Parcelable

@Parcelize
data class WpAttachmentItem(

	@field:SerializedName("href")
	val href: String? = null
) : Parcelable

@Parcelize
data class Links(

	@field:SerializedName("curies")
	val curies: List<CuriesItem?>? = null,

	@field:SerializedName("replies")
	val replies: List<RepliesItem?>? = null,

	@field:SerializedName("wp:term")
	val wpTerm: List<WpTermItem?>? = null,

	@field:SerializedName("about")
	val about: List<AboutItem?>? = null,

	@field:SerializedName("self")
	val self: List<SelfItem?>? = null,

	@field:SerializedName("collection")
	val collection: List<CollectionItem?>? = null,

	@field:SerializedName("wp:attachment")
	val wpAttachment: List<WpAttachmentItem?>? = null
) : Parcelable

@Parcelize
data class Guid(

	@field:SerializedName("rendered")
	val rendered: String? = null
) : Parcelable

@Parcelize
data class SelfItem(

	@field:SerializedName("href")
	val href: String? = null
) : Parcelable

@Parcelize
data class Content(

	@field:SerializedName("rendered")
	val rendered: String? = null,

	@field:SerializedName("protected")
	val jsonMemberProtected: Boolean? = null
) : Parcelable

@Parcelize
data class AboutItem(

	@field:SerializedName("href")
	val href: String? = null
) : Parcelable

@Parcelize
data class RepliesItem(

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("embeddable")
	val embeddable: Boolean? = null
) : Parcelable

@Parcelize
data class CuriesItem(

	@field:SerializedName("templated")
	val templated: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null
) : Parcelable

@Parcelize
data class Title(

	@field:SerializedName("rendered")
	val rendered: String? = null
) : Parcelable

@Parcelize
data class CouponDataResponseItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("template")
	val template: String? = null,

	@field:SerializedName("_links")
	val links: Links? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("used")
	val used: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: Title? = null,

	@field:SerializedName("coupon_affiliate")
	val couponAffiliate: String? = null,

	@field:SerializedName("coupon_url")
	val couponUrl: String? = null,

	@field:SerializedName("content")
	val content: Content? = null,

	@field:SerializedName("featured_media")
	val featuredMedia: Int? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("exclusive")
	val exclusive: @RawValue Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("coupon_printable")
	val couponPrintable: String? = null,

	@field:SerializedName("date_gmt")
	val dateGmt: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("coupon_code")
	val couponCode: String? = null,

	@field:SerializedName("modified_gmt")
	val modifiedGmt: String? = null,

	@field:SerializedName("coupon-category")
	val couponCategory:@RawValue List<Any?>? = null,

	@field:SerializedName("comment_status")
	val commentStatus: String? = null,

	@field:SerializedName("ping_status")
	val pingStatus: String? = null,

	@field:SerializedName("ctype")
	val ctype: String? = null,

	@field:SerializedName("acf")
	val acf: AcfResponse? = null,

	@field:SerializedName("expire")
	val expire: String? = null,


	var isExpire: Boolean? = null,

	@field:SerializedName("coupon-store")
	val couponStore: List<Int?>? = null,

	@field:SerializedName("guid")
	val guid: Guid? = null,

	@field:SerializedName("status")
	val status: String? = null,

	val itemClick: String? = null
) : Parcelable

@Parcelize
data class AcfResponse(

	@field:SerializedName("grab_deal")
	val grabDeal: String? = null,

	@field:SerializedName("copy_code")
	val copyCode: String? = null,

	@field:SerializedName("all_deals_button_text")
	val allDealsButtonText: String? = null,

	@field:SerializedName("bar_code")
	val barCode: @RawValue String? = null,

	@field:SerializedName("deal_field_text")
	val dealFieldText: String? = null,

	@field:SerializedName("discount_value")
	val discountValue: String? = null,

	@field:SerializedName("deal_or_coupon")
	val dealOrCoupon: String? = null,

	@field:SerializedName("main_attention_text")
	val mainAttentionText: String? = null,

	@field:SerializedName("coupon_deals_button_text")
	val couponDealsDuttonText: String? = null,

	@field:SerializedName("success_rate")
	val successRate: String? = null,

	@field:SerializedName("shop_now")
	val shopNow: String? = null
) : Parcelable