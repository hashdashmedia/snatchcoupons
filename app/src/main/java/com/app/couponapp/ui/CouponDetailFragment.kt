package com.app.couponapp.ui

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.viewbinding.ViewBinding
import com.app.couponapp.R
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.databinding.FragmentCouponDetailBinding
import com.bumptech.glide.Glide

class CouponDetailFragment : BaseFragment<FragmentCouponDetailBinding>() {
    private var couponData: CouponDataResponseItem?=null
    override fun getViewBinding()=FragmentCouponDetailBinding.inflate(layoutInflater)

    override fun observe() {}

    override fun init() {
        arguments?.let {
            couponData=it.getParcelable("couponData")
        }
        setScreenData()
    }

    private fun setScreenData() {
        with(dataBinding){
            couponData?.run {
                tvNoPer.text=acf?.discountValue?:""
                tvTitle.text=title?.rendered?:""
                tvCouponCode.text=couponCode?:""
                tvCopyCode.text=acf?.copyCode?:""
                tvCopyCode.text=acf?.shopNow?:""
                Glide.with(requireContext()).load(acf?.barCode?:"").into(ivBarcode)
                tvSuccessOne.text="${acf?.successRate} SUCCESS"
                tvUsedToday.text="USED TODAY $used"
                tvDescription.text=HtmlCompat.fromHtml(content?.rendered?:"",FROM_HTML_MODE_COMPACT)
            }
        }
    }

    override fun showBar()=false

    override fun showStatusBar()=true

    override fun removeBackArrowDrawer()=false

}