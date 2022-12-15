package com.app.couponapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.databinding.FragmentDealDetailBinding
import com.app.couponapp.util.makeGone
import com.app.couponapp.util.makeVisible
import com.app.couponapp.util.showMessage
import com.bumptech.glide.Glide


class DealDetailFragment : BaseFragment<FragmentDealDetailBinding>() {
    private var dealData: CouponDataResponseItem? = null
    private var tvDescMaxLinesCount = 0
    override fun getViewBinding() = FragmentDealDetailBinding.inflate(layoutInflater)

    override fun observe() {}

    override fun init() {
        arguments?.let {
            dealData = it.getParcelable("dealData")
        }
        setClickListeners()
        setScreenData()
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

        dataBinding.tvGrabDeal.setOnClickListener {
            openWebView(dealData?.couponAffiliate?:"")
        }
        dataBinding.tvGotoSite.setOnClickListener {
            openWebView(dealData?.couponAffiliate?:"")
        }
    }

    private fun openWebView(url:String){
        val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val chooser = Intent.createChooser(sendIntent, "Choose Your Browser")
        startActivity(chooser)
    }

    @SuppressLint("SetTextI18n")
    private fun setScreenData() {
        with(dataBinding) {
            dealData?.run {
                tvNoPer.text = acf?.discountValue ?: ""
                tvTitle.text = title?.rendered ?: ""
                tvGrabDeal.text=acf?.grabDeal?:""
                tvDealField.text=
                if(acf?.dealFieldText.isNullOrEmpty())
                    "FLAT ${acf?.discountValue} OFF"
                else
                    acf?.dealFieldText?:""

                Glide.with(requireContext()).load(acf?.barCode ?: "").into(ivBarcode)
                tvSuccessOne.text = "${acf?.successRate} SUCCESS"
                tvUsedToday.text = "USED TODAY $used"
                tvDescription.text = HtmlCompat.fromHtml(content?.rendered ?: "", FROM_HTML_MODE_COMPACT)
                tvDescription.makeVisible()
                tvDescMaxLinesCount = if(tvDescription.text.isNullOrEmpty()) 0 else tvDescription.maxLines
                if (tvDescMaxLinesCount > 3) {
                    tvShowMore.text = "+ Show more"
                    tvDescription.maxLines = 3
                } else {
                    tvShowMore.makeGone()
                }
            }
        }
    }

    override fun showBar() = false

    override fun showStatusBar() = true

    override fun removeBackArrowDrawer() = false

}