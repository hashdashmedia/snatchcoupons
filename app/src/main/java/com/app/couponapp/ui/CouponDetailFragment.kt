package com.app.couponapp.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.databinding.FragmentCouponDetailBinding
import com.app.couponapp.util.makeGone
import com.app.couponapp.util.makeVisible
import com.app.couponapp.util.openWebView
import com.app.couponapp.util.showMessage
import com.bumptech.glide.Glide


class CouponDetailFragment : BaseFragment<FragmentCouponDetailBinding>() {
    private var couponData: CouponDataResponseItem? = null
    private var tvDescMaxLinesCount = 0
    override fun getViewBinding() = FragmentCouponDetailBinding.inflate(layoutInflater)

    override fun observe() {}

    override fun init() {
        arguments?.let {
            couponData = it.getParcelable("couponData")
        }
        setClickListeners()
        setScreenData()
        (activity as MainActivity).couponDetailBannerAd()
    }

    private fun setClickListeners() {
        dataBinding.tvShowMore.setOnClickListener {
            dataBinding.tvShowMore.text =
                if (dataBinding.tvShowMore.text.toString().equals("+ Show more", ignoreCase = true)) {
                    dataBinding.tvDescription.maxLines = tvDescMaxLinesCount
                    "See less"
                } else {
                    dataBinding.tvDescription.maxLines =3
                    "+ Show more"
                }
        }

        dataBinding.tvCopyCode.setOnClickListener {
            requireContext().showMessage("COUPON CODE COPIED! ")
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copiedText", dataBinding.tvCouponCode.text)
            clipboard.setPrimaryClip(clip)
            openWebView(couponData?.couponAffiliate?:"")
        }
        dataBinding.tvShopNow.setOnClickListener {
            openWebView(couponData?.couponAffiliate?:"")
        }
        dataBinding.tvGotoSite.setOnClickListener {
            openWebView(couponData?.couponAffiliate?:"")
        }
    }

    private fun openWebView(url:String){
        val launchIntent=requireContext().packageManager.getLaunchIntentForPackage("com.aries.michaels")
        launchIntent?.let {
            startActivity(it)
        }?: run{
            requireContext().openWebView(url)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setScreenData() {
        with(dataBinding) {
            couponData?.run {
                tvNoPer.text = acf?.discountValue ?: ""
                tvTitle.text = title?.rendered ?: ""
                tvCouponCode.text = couponCode ?: ""
                tvCopyCode.text = acf?.copyCode ?: ""
                tvShopNow.text = acf?.shopNow ?: ""
                Glide.with(requireContext()).load(acf?.barCode ?: "").into(ivBarcode)
                tvSuccessOne.text = "${acf?.successRate} SUCCESS"
                tvUsedToday.text = "USED TODAY $used"
                tvDescription.text = HtmlCompat.fromHtml(content?.rendered ?: "", FROM_HTML_MODE_COMPACT)
                tvDescription.makeVisible()
                tvDescription.post {
                    tvDescMaxLinesCount = if(tvDescription.text.isNullOrEmpty()) 0 else tvDescription.lineCount
                    if (tvDescMaxLinesCount > 3) {
                        tvShowMore.text = "+ Show more"
                        tvDescription.maxLines = 3
                    } else {
                        tvShowMore.makeGone()
                    }
                }
            }
        }
    }

    override fun showBar() = false

    override fun showStatusBar() = true

    override fun removeBackArrowDrawer() = false

}