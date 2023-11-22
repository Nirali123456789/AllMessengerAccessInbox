package com.myapps.allsocialaccess.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.admob.intertial.AdmobInterstitial
import com.myapps.allsocialaccess.admob.intertial.AdmobInterstitialLoadListeners
import com.myapps.allsocialaccess.databinding.ActivitySplashScreenBinding
import com.myapps.allsocialaccess.utils.Constants

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>(),
    AdmobInterstitialLoadListeners {

    private var _decorView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        _decorView = window.decorView
        super.onCreate(savedInstanceState)
        SetUpAdmob()
        Log.d("TAG", "onCreate: "+divide(5.0, 2.0))

    }

    private fun setUpView() {
        if (!MainApplication.prefs1!!.isPrivacyAccepted) {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
            finish()
        }
        else if (!MainApplication.prefs1!!.isLangugaeShown) {
            startActivity(Intent(this, LanguageActivity::class.java))
            finish()
        }
        else {
            Handler(Looper.myLooper()!!).postDelayed(Runnable {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2500)

        }
    }

    private fun SetUpAdmob() {

        //Admob Interstiti al
        AdmobInterstitial.loadAd(this, getString(R.string.intertial_id), this)
        Handler(Looper.myLooper()!!).postDelayed(
            Runnable {
                setUpView()
            }, 2000
        )
    }

    override fun getViewBinding(inflater: LayoutInflater) =
        ActivitySplashScreenBinding.inflate(inflater)

    override fun adLoaded() {

    }

    override fun adFailed() {
        setUpView()
    }

    override fun adAlreadyLoaded() {

    }

    override fun adIsLoading() {

    }

    private fun divide(num: Double, den: Double): Double {
        return num / den;
    }

    override fun onResume() {
        Constants.hideSystemUI(_decorView!!)
        super.onResume()
    }
}