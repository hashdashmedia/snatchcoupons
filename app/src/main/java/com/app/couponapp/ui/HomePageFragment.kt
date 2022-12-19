package com.app.couponapp.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.app.couponapp.R
import com.app.couponapp.adapter.CouponItemAdapter
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.databinding.FragmentHomePageBinding
import com.app.couponapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomePageFragment : BaseFragment<FragmentHomePageBinding>() {
    private val couponItemAdapter by lazy {
        CouponItemAdapter(onCouponItemClick, (activity as MainActivity).getCurrentTab() as String)
    }
    private val couponViewModel by viewModels<CouponViewModel>()
    override fun getViewBinding()=FragmentHomePageBinding.inflate(layoutInflater)
    override fun observe() {
        couponViewModel.collectCouponData().launchAndCollectIn(this,Lifecycle.State.STARTED){it->
            when(it){
                is Resource.Success -> {
                    couponItemAdapter.submitList(filterList(it.data))
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
   private val onCouponItemClick:(CouponDataResponseItem)->Unit = {data->
       if(data.acf?.dealOrCoupon.equals("coupon",ignoreCase = true)){
           findNavController().navigate(R.id.navCouponDetail, bundleOf("couponData" to data))
       }else if(data.acf?.dealOrCoupon.equals("deal",ignoreCase = true)){
           findNavController().navigate(R.id.navDealDetail, bundleOf("dealData" to data))
       }
   }
    private fun filterList(list: List<CouponDataResponseItem>?): List<CouponDataResponseItem> ?{
        val currentTimeDate = Date().time
        val list = list?.sortedByDescending {
            it.acf?.discountValue?.dropLast(it.acf.discountValue.length - 1)
        }?.sortedByDescending{
            it.expire?.toDoubleOrNull()
        }?.map {
            val expTimeDate = (it.expire?.toLong()?:0)*1000
            it.isExpire=currentTimeDate>expTimeDate
            it
        }
        return list
    }

    override fun init() {
        (activity as MainActivity).hideShowBottomNav("show")
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