package com.myapps.allsocialaccess.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapps.allsocialaccess.repository.SocialMediaRepository

class SocialMediaViewModelFactory(private val repository: SocialMediaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SocialMediaViewModel::class.java)) {
            return SocialMediaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
