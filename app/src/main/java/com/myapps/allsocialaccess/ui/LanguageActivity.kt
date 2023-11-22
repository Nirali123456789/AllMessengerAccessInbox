package com.myapps.allsocialaccess.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.murgupluoglu.flagkit.FlagKit
import com.myapps.allsocialaccess.MainApplication.Companion.prefs1
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.adapters.LanguageAdapter
import com.myapps.allsocialaccess.admob.nativeAd.NativeAds
import com.myapps.allsocialaccess.admob.nativeAd.NativeListener
import com.myapps.allsocialaccess.databinding.ActivityLanguageBinding
import com.myapps.allsocialaccess.interfaces.AdapterOnClick
import com.myapps.allsocialaccess.models.Language
import com.myapps.allsocialaccess.utils.Constants
import com.myapps.allsocialaccess.utils.Constants.Companion.languageCodes

class LanguageActivity : BaseActivity<ActivityLanguageBinding>(),AdapterOnClick {

    private lateinit var languageList: ArrayList<Language>
    private lateinit var languageAdapter: LanguageAdapter
    private var isNativeAdLoaded = false



    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        setAdapter()
        onClick()
        if (!isNativeAdLoaded) {
            nativeAd() // Load the native ad if it hasn't been loaded
            isNativeAdLoaded = true // Set the flag to indicate that the ad has been loaded
        }

    }

    override fun getViewBinding(inflater: LayoutInflater) = ActivityLanguageBinding.inflate(inflater)

    private fun onClick() {
        binding.done!!.setOnClickListener {
            prefs1!!.isLangugaeShown = true
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setAdapter() {
        languageList = ArrayList()
        foodListItems()

        languageAdapter = LanguageAdapter(this, languageList, this, BottomSheetDialog(this))
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = languageAdapter

    }

    private fun foodListItems() {
        val resourceId = FlagKit.getResId(this, "us")

        languageList.add(Language(resourceId, "USA"))
        languageList.add(Language(FlagKit.getResId(this, "in"), "Hindi"))
        languageList.add(Language(FlagKit.getResId(this, "ar"), "Arabic"))
        languageList.add(Language(FlagKit.getResId(this, "de"), "German"))
        languageList.add(Language(FlagKit.getResId(this, "ru"), "Russian"))
        languageList.add(Language(FlagKit.getResId(this, "pt"), "Portuguese"))

    }

    fun nativeAd() {
        NativeAds.loadAd(this, getString(R.string.admobNative), object : NativeListener {
            override fun adNativeLoaded(currentNativeAd: NativeAd) {
                binding.fram.visibility = View.VISIBLE
                binding.fram.visibility = View.VISIBLE
                isNativeAdLoaded =true
                val layoutInflater =
                    layoutInflater.inflate(R.layout.ad_unified, null) as NativeAdView
                NativeAds.populateNativeAdView(currentNativeAd, layoutInflater)
                binding.fram.removeAllViews()
                binding.fram.addView(layoutInflater)
                Log.d("NativeAds", "adNativeLoaded")

            }

            override fun adNativeFailed() {
                binding.fram.visibility = View.GONE
                Log.d("NativeAds", "adNativeFailed")


            }


            override fun adNativeIsLoading() {
                Log.d("NativeAds", "adNativeIsLoading")


            }

            override fun onNativeValidate() {
                binding.fram.visibility = View.GONE
                Log.d("NativeAds", "onNativeValidate")


            }

        })

    }

    override fun onClick(item: String) {

    }

    override fun onClickLanguage(item: String, bottomSheetDialog: BottomSheetDialog,position:Int) {
        //Constants.setLocal(this!!, languageCodes[position])
        prefs1!!.languageCode = languageCodes[position]
    }


}