package com.app.couponapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.app.couponapp.util.BaseMethods

abstract class BaseFragment<VB : ViewBinding> : Fragment(), BaseMethods {
    var viewSave: View?=null
    lateinit var dataBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        getViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        showSBar()
        super.onActivityCreated(savedInstanceState)
    }
    private fun showSBar(){
        if(showStatusBar())
            (activity as MainActivity)?.supportActionBar?.show()
        else
            (activity as MainActivity)?.supportActionBar?.hide()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = getViewBinding() as VB
        viewSave=dataBinding.root
        //getViewBinding()
        setLiveDataValues()
        init()
        return viewSave
    }
}