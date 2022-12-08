package com.app.couponapp.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.couponapp.adapter.CouponItemAdapter
import com.app.couponapp.databinding.FragmentHomePageBinding
import com.app.couponapp.util.Resource
import com.app.couponapp.util.makeGone
import com.app.couponapp.util.makeVisible
import com.app.couponapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomePageFragment : BaseFragment<FragmentHomePageBinding>() {
    private val couponItemAdapter by lazy { CouponItemAdapter() }
    private val couponViewModel by viewModels<CouponViewModel>()
    override fun getViewBinding()=FragmentHomePageBinding.inflate(layoutInflater)
    override fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                couponViewModel.collectCouponData().collect{
                    when(it){
                        is Resource.Success -> {
                            couponItemAdapter.submitList(it.data)
                            dataBinding.progressCircular.makeGone()
                        }
                        is Resource.Loading ->  dataBinding.progressCircular.makeVisible()
                        is Resource.Error ->  {
                            requireContext().showToast(it.message)
                            dataBinding.progressCircular.makeGone()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
    override fun init() {
        setCouponAdapter()
        getCouponsListing()
    }

    private fun getCouponsListing() {
        couponViewModel.getCouponsListing(48)
    }

    private fun setCouponAdapter() {
        dataBinding.rvHome.adapter=couponItemAdapter
    }

    override fun showBar()=false
    override fun showStatusBar()=true
    override fun getViewModel() {}
    override fun removeBackArrowDrawer()=false
}