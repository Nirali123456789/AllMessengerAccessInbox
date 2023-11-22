package com.myapps.allsocialaccess.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.databinding.ActivityPrivacyPolicyBinding
import com.myapps.allsocialaccess.utils.Constants.Companion.hideSystemUI
import com.myapps.allsocialaccess.utils.Constants.Companion.showSystemUI


class PrivacyPolicyActivity : BaseActivity<ActivityPrivacyPolicyBinding>() {
    var isCheckedpp = false
    private var _decorView: View? = null
    private var _tapDetector: GestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        _decorView = window.decorView
        hideSystemUI(_decorView!!)
        super.onCreate(savedInstanceState)
        buttonClick()
        eventTouch()

    }

    private fun eventTouch() {


        _tapDetector = GestureDetector(this,
            object : SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    val visible =
                        _decorView!!.getSystemUiVisibility() and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION === 0
                    if (visible) hideSystemUI(_decorView!!) else showSystemUI(_decorView!!)
                    return true
                }
            })
    }

    override fun getViewBinding(inflater: LayoutInflater) =
        ActivityPrivacyPolicyBinding.inflate(inflater)

    private fun buttonClick() {
        binding!!.btnGetStarted.setBackgroundResource(R.drawable.bg_round_corner)

        binding!!.confirm.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            Log.d("buttonClick", "buttonClick: " + isChecked)
            isCheckedpp = isChecked
            binding!!.btnGetStarted.isEnabled = isChecked
            binding!!.btnGetStarted.setBackgroundResource(if (isChecked) R.drawable.bg_round_dark else R.drawable.bg_round_corner)
            binding!!.btnGetStarted.setTextColor(
                resources.getColor(
                    if (isChecked) R.color.white else R.color.grey,
                    getTheme()
                )
            )
        }

        binding!!.btnGetStarted.setOnClickListener { v: View? ->

            if (isCheckedpp) {
                MainApplication.instance.prefs.isPrivacyAccepted = true
                if (MainApplication.prefs1!!.isLangugaeShown) {

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, LanguageActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Please accept privacy policy", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        _tapDetector!!.onTouchEvent(ev!!)
        return super.dispatchTouchEvent(ev)
    }

    override fun onResume() {
        hideSystemUI(_decorView!!)
        super.onResume()
    }
}