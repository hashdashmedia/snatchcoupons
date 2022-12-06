package com.app.couponapp.ui

import com.app.couponapp.databinding.FragmentHomePageBinding


class HomePageFragment : BaseFragment<FragmentHomePageBinding>() {
    override fun getViewBinding()=FragmentHomePageBinding.inflate(layoutInflater)
    override fun init() {}
    override fun showBar()=false
    override fun showStatusBar()=true
    override fun getViewModel() {}
    override fun observe() {}
    override fun removeBackArrowDrawer()=false
}