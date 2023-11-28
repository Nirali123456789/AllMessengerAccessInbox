package com.myapps.allsocialaccess.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.databinding.ActivityWebViewBinding
import com.myapps.allsocialaccess.utils.Constants


class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    private var _decorView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        _decorView = window.decorView

        super.onCreate(savedInstanceState)
        setUpView()
        val customBackCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webview.canGoBack()) {
                    binding.webview.goBack()
                } else {
                    finish()
                    overridePendingTransition(
                        R.anim.slide_in_left,
                        R.anim.slide_out
                    );
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, customBackCallback)
    }

    private fun setUpView() {
        var value = intent.getStringExtra("package")
        setUpWebVew(value)
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpWebVew(value: String?) {
        binding!!.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

        if (MainApplication.prefs1!!.theme.equals("dark")) {
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(
                    binding!!.webview.settings,
                    WebSettingsCompat.FORCE_DARK_ON
                );
            }
        }
        binding!!.webview.settings.javaScriptEnabled = true
        binding!!.webview.settings.useWideViewPort = true
        binding!!.webview.settings.loadWithOverviewMode = true
        binding!!.webview.setInitialScale(1);
        binding!!.webview.loadUrl("$value")
        binding!!.webview.isFocusable = true
        binding!!.webview.isFocusableInTouchMode = true


    }

    override fun getViewBinding(inflater: LayoutInflater) = ActivityWebViewBinding.inflate(inflater)

    override fun onResume() {
//        Constants.hideSystemUI(_decorView!!)
        super.onResume()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.binding.webview.canGoBack()) {
            this.binding.webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}