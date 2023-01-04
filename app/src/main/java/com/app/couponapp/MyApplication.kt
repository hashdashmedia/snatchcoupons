package com.app.couponapp

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AudienceNetworkAds.initialize(this@MyApplication)
        MobileAds.initialize(this@MyApplication)
    }
}