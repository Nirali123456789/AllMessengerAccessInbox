package com.myapps.allsocialaccess.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.myapps.allsocialaccess.fragments.HomeFragment
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.databinding.ActivityMainBinding
import com.myapps.allsocialaccess.fragments.SettingFragment
import com.myapps.allsocialaccess.utils.Constants
import com.myapps.allsocialaccess.utils.Constants.Companion.showExitConfirmationDialog
import com.myapps.allsocialaccess.utils.LanguageContextWrapper

class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var currentFragment: Fragment
    private lateinit var drawerLayout: DrawerLayout
    private var _decorView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        _decorView = window.decorView
        super.onCreate(savedInstanceState)
        setView()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, HomeFragment()).addToBackStack("Home").commit()
            binding.navView.setCheckedItem(R.id.nav_home)
        }
        onBackPress()
    }

    private fun onBackPress() {
        val customBackCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else if (::currentFragment.isInitialized && currentFragment.tag != "HomeFragment") {
                    setFragment(HomeFragment(),"HomeFragment",getString(R.string.home))
                } else {
                    if (!MainApplication.prefs1?.userHasRatedApp!!) {
                        Constants.rateUsDialog(this@MainActivity)
                    } else {
                        showExitConfirmationDialog(this@MainActivity)

                    }
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, customBackCallback)
    }

    private fun setView() {
        if (!MainApplication.prefs1!!.firstSelection) {
            startActivity(Intent(this, SocialMediaAppActivity::class.java))
           // overridePendingTransition(R.anim.anim_right, R.anim.anim_left);
        }
        drawerLayout = binding.drawerLayout
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, HomeFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        binding.navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> setFragment(HomeFragment(), "HomeFragment",getString(R.string.home))

            R.id.nav_apps -> startActivity(Intent(this, SocialMediaAppActivity::class.java))
            R.id.nav_settings -> {
                binding.customTitle.text = getString(R.string.settings)
                setFragment(SettingFragment(), "SettingFragment",getString(R.string.settings))
            }

            R.id.nav_share -> Constants.shareUs(this)

            R.id.nav_rate -> Constants.rateUs(this)

            R.id.nav_logout -> {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacypolicy)))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setFragment(fragment: Fragment, tag: String,title:String) {
        currentFragment = fragment
        binding.customTitle.text = title
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment,tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onResume() {
        //Constants.hideSystemUI(_decorView!!)
        super.onResume()
    }

    override fun getViewBinding(inflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)

    override fun attachBaseContext(newBase: Context) {
        val selectedLanguage = MainApplication.instance.prefs.languageCode
        super.attachBaseContext(LanguageContextWrapper.wrap(newBase, selectedLanguage!!))
    }
}