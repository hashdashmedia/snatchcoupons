package com.app.couponapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.app.couponapp.BuildConfig
import com.app.couponapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


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

fun Context.shareApp(data:String){
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(
        Intent.EXTRA_TEXT,data)
    intent.type = "text/plain"
    startActivity(intent)
}

fun Context.openWebView(url: String) {
    val playIntent: Intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(url)
    }
    try {
        startActivity(playIntent)
    } catch (e: Exception) {
        // handle the exception
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

fun Context.showMessage(msg:String){ Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()}

inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}

@SuppressLint("InflateParams")
fun  <VB:ViewBinding> Context.customAlertDialog(@StyleRes style:Int?=0,binding:VB,
                                  callback:(VB,AlertDialog)->Unit){
    if (style != null) {
        AlertDialog.Builder(this,style).create()
            .apply {
                setView(binding.root)
                setCanceledOnTouchOutside(false)
                callback(binding,this)
            }
    }

}
