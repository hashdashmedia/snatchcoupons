package com.app.couponapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.app.couponapp.databinding.SplashFragmentBinding

class SplashFragment : BaseFragment<SplashFragmentBinding>() {
    private val splashDelay = 3000L

    override fun getViewBinding()=SplashFragmentBinding.inflate(layoutInflater)
    override fun getFragmentLayout()=R.layout.splash_fragment
    override fun observe() {}
    override fun init() {
        showOrHideSplash()
    }
    override fun showBar()=false
    override fun showStatusBar()=false
    override fun showMenu()=false
    override fun setMenu()=0
    override fun setMixPanelFlag()=""
    override fun favOperation(view: View) {}
    override fun removeBackArrowDrawer()=true
    private fun showOrHideSplash() {
        var mHandler = Handler(Looper.getMainLooper())
            mHandler.postDelayed({
                val navOptions =  NavOptions.Builder().setPopUpTo(R.id.nav_splash, inclusive = true).build()
               findNavController().navigate(R.id.navHomePage,null,navOptions)
               // mHandler=null
            }, splashDelay)
    }
}