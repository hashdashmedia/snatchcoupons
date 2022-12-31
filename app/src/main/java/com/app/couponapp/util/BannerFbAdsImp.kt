package com.app.couponapp.util

import android.util.Log
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.NativeAdListener

class BannerFbAdsImp constructor(private val adListener: CustomAdsListener): NativeAdListener {
    override fun onError(p0: Ad?, p1: AdError?) {
       Log.e("BannerFbAdsImp=", p1?.errorMessage.toString())
       // p0?.loadAd()
    }

    override fun onAdLoaded(p0: Ad?) {
        Log.e("BannerFbAdsImp=", "onAdLoaded")
        adListener.onAdLoad(p0)
    }

    override fun onAdClicked(p0: Ad?) {

    }

    override fun onLoggingImpression(p0: Ad?) {

    }

    override fun onMediaDownloaded(p0: Ad?) {

    }

}
interface CustomAdsListener{
    fun onAdLoad(p0: Ad?)
}