package com.myapps.allsocialaccess.interfaces

import com.myapps.allsocialaccess.models.SocialMediaApp

interface AdapterCallback {
    fun onItemClicked(data: SocialMediaApp)
}