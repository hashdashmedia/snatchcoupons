package com.app.couponapp.util

import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideStatusActionBar(){
    supportActionBar?.hide()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}
fun Context.showToast(message:String?=null){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
fun View.makeVisible(){
    visibility = View.VISIBLE
}
fun View.makeGone(){
    visibility = View.GONE
}
fun View.isVisible()=visibility  == View.VISIBLE
