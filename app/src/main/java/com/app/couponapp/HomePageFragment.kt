package com.app.couponapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
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