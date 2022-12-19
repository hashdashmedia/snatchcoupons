package com.app.couponapp.ui

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.app.couponapp.R
import com.app.couponapp.databinding.SplashFragmentBinding
import com.app.couponapp.ui.BaseFragment

class SplashFragment : BaseFragment<SplashFragmentBinding>() {
    private val splashDelay = 3000L

    override fun getViewBinding()=SplashFragmentBinding.inflate(layoutInflater)
    override fun getFragmentLayout()= R.layout.splash_fragment
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
        val mHandler = Handler(Looper.getMainLooper())
            mHandler.postDelayed({
                val navOptions =  NavOptions.Builder().setPopUpTo(R.id.nav_splash, inclusive = true).build()
               findNavController().navigate(R.id.navHomePage,null,navOptions)
               // mHandler=null
            }, splashDelay)
    }
}