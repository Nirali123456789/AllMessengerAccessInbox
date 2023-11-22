/**Created By Nirali Pandya
 * Date :2023-11-22
 * Time :9:24 a.m.
 * Project Name :All Messenger Access-Inbox
 *
 */
package com.myapps.allsocialaccess.utils

import android.app.Activity
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.utils.themes.Companion.changeTheme

class themes {

    companion object {
        fun Activity.changeTheme() {
            // Switch to the opposite theme mode
            val newNightMode =
                if (MainApplication.prefs1!!.theme.equals("dark")) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            val currentNightMode = AppCompatDelegate.getDefaultNightMode()
            if (currentNightMode != newNightMode) {

                // Set the new theme mode
                AppCompatDelegate.setDefaultNightMode(newNightMode)

            }
        }

        fun Activity.themeDialog() {
            val bottomSheetDialog = BottomSheetDialog(this!!)
            bottomSheetDialog.setContentView(R.layout.list_theme)

            var dark = bottomSheetDialog.findViewById<RadioButton>(R.id.dark)!!
            var light = bottomSheetDialog.findViewById<RadioButton>(R.id.light)!!

            if (MainApplication.prefs1!!.theme.equals("dark")) {
                dark.isChecked = true
            } else {
                light.isChecked = true
            }

            dark.setOnCheckedChangeListener { p0, p1 ->
                if (p1) {
                    bottomSheetDialog.dismiss()
                    MainApplication.prefs1!!.theme = "dark"
                    changeTheme()
                }
            }

            light.setOnCheckedChangeListener { p0, p1 ->
                if (p1) {
                    bottomSheetDialog.dismiss()
                    MainApplication.prefs1!!.theme = "light"
                    changeTheme()
                }
            }


            if (!isFinishing)
                bottomSheetDialog.show()
        }

        fun Activity.setTheme():String {
            var theme = MainApplication.prefs1!!.theme
            var prefferedtheme: String
            if (theme.equals("light")) {
                prefferedtheme = getString(R.string.light)

            } else {
                prefferedtheme = getString(R.string.dark)
            }
           return prefferedtheme
        }
    }
}