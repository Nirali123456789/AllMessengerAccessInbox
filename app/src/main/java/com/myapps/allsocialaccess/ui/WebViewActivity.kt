package com.myapps.allsocialaccess.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.databinding.ActivityWebViewBinding
import com.myapps.allsocialaccess.fragments.WebFragment
import com.myapps.allsocialaccess.utils.Constants

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {
    private var _decorView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        _decorView = window.decorView
        super.onCreate(savedInstanceState)
        setUpView()
        val customBackCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }

        onBackPressedDispatcher.addCallback(this, customBackCallback)
    }

    private fun setUpView() {
        var value = intent.getStringExtra("package")
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, WebFragment(value!!))
            .addToBackStack(null).commit()
        binding.back.setOnClickListener {
            finish()
        }
    }

    override fun getViewBinding(inflater: LayoutInflater) = ActivityWebViewBinding.inflate(inflater)

    override fun onResume() {
        Constants.hideSystemUI(_decorView!!)
        super.onResume()
    }
}