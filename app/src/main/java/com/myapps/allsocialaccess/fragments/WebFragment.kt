package com.myapps.allsocialaccess.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.myapps.allsocialaccess.databinding.FragmentWebviewBinding
import android.webkit.WebView
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

class WebFragment(private val packageName:String) : Fragment() {
    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        setView()
        return binding!!.root
    }

    private fun setView() {
        binding!!.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?): Boolean {
                return false
            }
        }
        if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(binding!!.webview.settings, WebSettingsCompat.FORCE_DARK_ON);
        }
        binding!!.webview.settings.javaScriptEnabled = true
        binding!!.webview.settings.useWideViewPort = true
        binding!!.webview.settings.loadWithOverviewMode = true
        binding!!.webview.setInitialScale(1);
        binding!!.webview.loadUrl("$packageName")
        Log.d("setView", "setView: $packageName")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


