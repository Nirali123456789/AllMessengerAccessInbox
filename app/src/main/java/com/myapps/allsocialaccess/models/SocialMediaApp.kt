package com.myapps.allsocialaccess.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_apps")
data class SocialMediaApp(val name: String, @PrimaryKey val packageName: String,
                          val iconUrl: Int, var selected: Boolean = false  )
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocialMediaApp

        if (name != other.name) return false
        if (packageName != other.packageName) return false
        if (iconUrl != other.iconUrl) return false


        return true
    }
}

