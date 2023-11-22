/**Created By Nirali Pandya
 * Date :2023-09-24
 * Time :6:38 p.m.
 * Project Name :All Messenger Access-Inbox
 *
 */
package com.myapps.allsocialaccess.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.myapps.allsocialaccess.R

abstract class BaseActivity <B : ViewBinding> : AppCompatActivity(){
    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDarkLight)
        super.onCreate(savedInstanceState)
        _binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

    }

    abstract fun getViewBinding(inflater: LayoutInflater): B

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }

}