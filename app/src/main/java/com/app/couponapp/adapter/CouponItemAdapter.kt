package com.app.couponapp.adapter

import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.couponapp.R
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.databinding.HomeRvItemBinding
import com.app.couponapp.util.makeGone
import java.util.*


class CouponItemAdapter(
    private val onCouponItemClick: (CouponDataResponseItem,Boolean) -> Unit,
    private val currentTab: String
) : ListAdapter<CouponDataResponseItem, CouponItemAdapter.CouponHolder>(DiffUtilCallback()) {



    class DiffUtilCallback: DiffUtil.ItemCallback<CouponDataResponseItem>() {
        override fun areItemsTheSame(oldItem: CouponDataResponseItem, newItem: CouponDataResponseItem): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: CouponDataResponseItem, newItem: CouponDataResponseItem): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponHolder {
       val binding = HomeRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return CouponHolder(binding)
    }

    override fun onBindViewHolder(holder: CouponHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
    inner class CouponHolder(private val binding: HomeRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                    onCouponItemClick(getItem(adapterPosition),false)
            }
            binding.ivShare.setOnClickListener {
                    onCouponItemClick(getItem(adapterPosition),true)
            }
        }

        fun bindItem(item: CouponDataResponseItem?) {
            if((currentTab.equals("deals",ignoreCase = true) && item?.acf?.dealOrCoupon.equals("coupon",ignoreCase = true))
                ||(currentTab.equals("coupons",ignoreCase = true) && item?.acf?.dealOrCoupon.equals("deal",ignoreCase = true))){
                binding.root.makeGone()
                binding.root.layoutParams = RecyclerView.LayoutParams(0, 0)
            }else{
              setItemData(item)
            }
        }

        private fun setItemData(item: CouponDataResponseItem?) {
            with(binding){
                item?.apply {
                    tvTakeExtra.text = acf?.mainAttentionText?:""
                    tvTitle.text = title?.rendered?:""
                    tvUsedToday.text ="USED TODAY ${used?:0}"
                    tvNoPer.text=acf?.discountValue
                    tvShowCouponCode.text=acf?.couponDealsDuttonText
                    tvSuccessOne.text="${acf?.successRate} SUCCESS"
                    val expTimeDate = (expire?.toLong()?:0)*1000
                    binding.tvExpDate.text = "Expires: ${DateFormat.format("dd/MM/yyyy",expTimeDate)}"
                    if(isExpire == true){
                        binding.tvShowCouponCode.isEnabled=false
                        binding.ivShare.isEnabled=false
                        binding.tvShowCouponCode.setBackgroundColor(Color.GRAY)
                        binding.tvNoPer.setTextColor(Color.GRAY)
                        binding.ivShare.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.share_grey_bg_icon))
                        binding.root.isEnabled=false
                    }
                    if(couponCode.isNullOrEmpty()){
                        tvCouponCode.makeGone()
                    }else{
                        tvCouponCode.text = couponCode.takeLast(3)
                    }
                }
            }
        }
    }
}
