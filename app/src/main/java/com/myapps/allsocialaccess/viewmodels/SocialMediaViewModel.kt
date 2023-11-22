package com.myapps.allsocialaccess.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.allsocialaccess.models.SocialMediaApp
import com.myapps.allsocialaccess.repository.SocialMediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SocialMediaViewModel(private val socialMediaRepository: SocialMediaRepository) : ViewModel() {
    private val socialMediaAppsLiveData = MutableLiveData<List<SocialMediaApp>>()
    val socialMediaApps: LiveData<List<SocialMediaApp>> get() = socialMediaAppsLiveData

    fun fetchSocialMediaApps(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val apps = socialMediaRepository.getSocialMediaApps(context)
            socialMediaAppsLiveData.postValue(apps)
        }
    }
}
