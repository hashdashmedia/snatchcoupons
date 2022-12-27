package com.app.couponapp.ui

import android.annotation.SuppressLint
import android.content.UriMatcher
import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.app.couponapp.databinding.FragmentWebViewBinding
import com.app.couponapp.util.makeGone
import com.app.couponapp.util.makeVisible


class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {
    var urlLink:String?=null
    override fun getViewBinding() = FragmentWebViewBinding.inflate(layoutInflater)
    override fun observe() {}
    @SuppressLint("SetJavaScriptEnabled")
    override fun init() {
        arguments?.let {
           urlLink= it.getString("url")
        }
        dataBinding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient=webViewClientObject
            urlLink?.let { loadUrl(it) }
        }
    }
    private var webViewClientObject=object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            dataBinding.progressCircular.makeVisible()
        }
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            dataBinding.progressCircular.makeGone()
        }
    }
    override fun showBar() = false
    override fun showStatusBar() = true
    override fun removeBackArrowDrawer() = false
}