package com.app.couponapp

import android.app.Application
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the Audience Network SDK

        AudienceNetworkAds.initialize(this)

    }
}