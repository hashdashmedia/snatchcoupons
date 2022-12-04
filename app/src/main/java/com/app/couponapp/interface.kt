package com.app.couponapp

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding

interface BaseMethods {
    fun getFragmentLayout(): Int{return 0}

    /**
     * This method will be called in onViewCreated of Fragment.
     * */
    fun getViewBinding():ViewBinding

    /**
     * This method will be called in onCreate of Fragment.
     * */
    fun getViewModel() {

    }

    /**
     * This method is for any livedata observables, will be called in onCreate of Fragment.
     * */
    abstract fun observe()

    /**
     * This method will be called in onViewCreated of Fragment.
     * */
    fun setLiveDataValues() {

    }

    /**
     * This method will be called in onViewCreated of Fragment.
     * */
    fun init()


    fun showError(showError: String) {

    }

     fun showMenu(): Boolean{return false}
     fun setMenu(): Int{return 0}
     fun setMixPanelFlag(): String{return ""}

     fun isFav() = MutableLiveData<Boolean>(false)

    abstract fun showBar(): Boolean

    abstract fun showStatusBar(): Boolean

     fun favOperation(view: View){}

    abstract fun removeBackArrowDrawer():Boolean
}