package com.myapps.allsocialaccess.repository

import android.content.Context
import com.myapps.allsocialaccess.models.SocialMediaApp

interface SocialMediaRepository {
    suspend fun getSocialMediaApps(context: Context): List<SocialMediaApp>
}
