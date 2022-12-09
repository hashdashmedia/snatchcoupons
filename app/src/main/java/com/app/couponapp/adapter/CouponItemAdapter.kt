package com.app.couponapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.databinding.HomeRvItemBinding
import java.util.*


class CouponItemAdapter: ListAdapter<CouponDataResponseItem, CouponItemAdapter.CouponHolder>(DiffUtilCallback()) {

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

        fun bindItem(item: CouponDataResponseItem?) {
            with(binding){
                item?.apply {
                    tvTitle.text = title?.rendered?:""
                    tvUsedToday.text ="USED TODAY ${used?:0}"
                    tvCouponCode.text = couponCode?:""
                }
            }
            setExpireDate(item?.expire)
        }

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        private fun setExpireDate(expire: String?) {
          expire.isNullOrEmpty().takeIf { false }.run {
              val currentTimeDate = Date().time
              val expTimeDate = (expire?.toLong()?:0)*1000
              binding.tvExpDate.text = "Expires: ${DateFormat.format("dd/MM/yyyy",expTimeDate)}"
              if(currentTimeDate > expTimeDate){
                  binding.tvShowCouponCode.isEnabled=false
                  binding.tvShowCouponCode.setBackgroundColor(Color.GRAY)
              }
          }
       }
    }
}
