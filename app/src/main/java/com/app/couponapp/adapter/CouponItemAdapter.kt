package com.app.couponapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.couponapp.data.model.CouponDataResponseItem
import com.app.couponapp.databinding.HomeRvItemBinding


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

    override fun onBindViewHolder(holder: CouponHolder, position: Int) {}

    inner class CouponHolder(binding: HomeRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
