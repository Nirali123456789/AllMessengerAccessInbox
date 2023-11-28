package com.myapps.allsocialaccess.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.MainApplication.Companion.database
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.adapters.SocialMediaAppAdapter
import com.myapps.allsocialaccess.databinding.ActivitySocialMediaAppBinding
import com.myapps.allsocialaccess.fragments.WebFragment
import com.myapps.allsocialaccess.interfaces.AdapterCallback
import com.myapps.allsocialaccess.models.SocialMediaApp
import com.myapps.allsocialaccess.repositoryImpl.SocialMediaRepositoryImpl
import com.myapps.allsocialaccess.utils.Constants
import com.myapps.allsocialaccess.utils.Constants.Companion.setStatusBarColor
import com.myapps.allsocialaccess.viewmodels.SocialMediaViewModel
import com.myapps.allsocialaccess.viewmodels.SocialMediaViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SocialMediaAppActivity : BaseActivity<ActivitySocialMediaAppBinding>(), AdapterCallback {
    private lateinit var viewModel: SocialMediaViewModel
    private var _decorView: View? = null

    private val adapter = SocialMediaAppAdapter(emptyList(), this)

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
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Instantiate the repository and ViewModel
        val repository = SocialMediaRepositoryImpl()
        viewModel = ViewModelProvider(this, SocialMediaViewModelFactory(repository))
            .get(SocialMediaViewModel::class.java)

        viewModel.socialMediaApps.observe(this) { apps ->
            lifecycleScope.launch {
                val selectedApps = getSelectedAppsFromDatabase()

                withContext(Dispatchers.Main) {
                    adapter.updateData(apps, selectedApps)
                }

            }
        }
        onClick()


        viewModel.fetchSocialMediaApps(this)
    }

    private fun onClick() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
        binding.back.setOnClickListener {
            callback.isEnabled = true
            onBackPressed()
        }
        binding.done.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val selectedApps = adapter.getSelectedItems()
                    if (selectedApps.isNotEmpty()) {
                        MainApplication.prefs1!!.firstSelection = true
                    }
                    for (app in selectedApps) {
                        val selectedAppEntity = app
                        database.selectedAppDao().insertSelectedApp(selectedAppEntity)
                    }
                    val removedApps = adapter.getRemovedItems()
                    for (app in removedApps) {
                        database.selectedAppDao().deleteSelectedApp(app)
                    }

                    finish()
                }

            }
        }
    }


    private suspend fun getSelectedAppsFromDatabase(): List<SocialMediaApp> {
        return withContext(Dispatchers.IO) {
            database.selectedAppDao().getAllSelectedApps()
        }
    }

    override fun getViewBinding(inflater: LayoutInflater) =
        ActivitySocialMediaAppBinding.inflate(inflater)

    override fun onItemClicked(data: SocialMediaApp) {
        startActivity(Intent(this, WebViewActivity::class.java).putExtra("package",data.packageName))


    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragmentCount = fragmentManager.backStackEntryCount

        if (fragmentCount > 0) {
            // If there are fragments on the back stack, pop the top fragment
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out
            );
        }
    }

    override fun onResume() {
        super.onResume()
    }

}