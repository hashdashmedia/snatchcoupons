package com.app.couponapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.couponapp.databinding.SplashFragmentBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashDelay = 3000L
    private lateinit var binding: SplashFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showOrHideSplash()
    }

    private fun showOrHideSplash() {
        val mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, splashDelay)
    }
}