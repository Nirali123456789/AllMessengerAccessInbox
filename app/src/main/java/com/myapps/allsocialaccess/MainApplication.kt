package com.myapps.allsocialaccess

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.google.android.gms.ads.MobileAds
import com.myapps.allsocialaccess.admob.AppOpenManager
import com.myapps.allsocialaccess.room.AppDatabase
import com.myapps.allsocialaccess.utils.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {


    public val prefs: Prefs by lazy {
        prefs1!!
    }
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val isNightModeEnabled = false

    companion object {
        var prefs1: Prefs? = null
        lateinit var instance: MainApplication
        lateinit var database: AppDatabase


    }

    fun isNightModeEnabled(): Boolean {
        return isNightModeEnabled
    }

    override fun onCreate() {
        super.onCreate()
        prefs1 = Prefs(applicationContext)
        instance = this
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database").build()
        setUp()


        var theme = prefs1!!.theme
        Log.d("TAG", "onCreate:>>> " + theme)

        if (theme.equals("dark")) {
            //setTheme(R.style.Theme_AllMessengerAccessInboxNight)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else if (theme.equals("light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //setTheme(R.style.Theme_AllMessengerAccessInbox)
        }

    }

    private fun setUp() {
        MobileAds.initialize(this)
        AppOpenManager(this)
    }


}