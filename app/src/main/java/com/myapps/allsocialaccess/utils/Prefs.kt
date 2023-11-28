package com.myapps.allsocialaccess.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context)
{
    private var APP_PREF_INT_EXAMPLE = "com.example.aie_mailassistant";
    private var IsLanguageShown= "IsLanguageShown";
    private var isPrivacyAccept="isPrivacyAccepted"
    private var isRate= "IsRate"
    private  var selection ="firstSelection"

    private val preferences: SharedPreferences = context.getSharedPreferences(APP_PREF_INT_EXAMPLE,Context.MODE_PRIVATE)



    var isLangugaeShown: Boolean
        get() = preferences.getBoolean(IsLanguageShown, false)
        set(value) = preferences.edit().putBoolean(IsLanguageShown, value).apply()

    var isPrivacyAccepted: Boolean
        get() = preferences.getBoolean(isPrivacyAccept, false)
        set(value) = preferences.edit().putBoolean(isPrivacyAccept, value).apply()

    var languageCode: String?
        get() = preferences.getString("languageCode", "en")
        set(value) = preferences.edit().putString("languageCode", value!!).apply()

    var theme: String?
        get() = preferences.getString("theme", "light")
        set(value) = preferences.edit().putString("theme", value!!).apply()

    var userHasRatedApp: Boolean
        get() = preferences.getBoolean(isRate, false)
        set(value) = preferences.edit().putBoolean(isRate, value).apply()


    var firstSelection: Boolean
        get() = preferences.getBoolean(selection, false)
        set(value) = preferences.edit().putBoolean(selection, value).apply()

    var position: Int?
        get() = preferences.getInt("position", -2)
        set(value) = preferences.edit().putInt("position", value!!).apply()

}