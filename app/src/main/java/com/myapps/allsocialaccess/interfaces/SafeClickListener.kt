package com.myapps.allsocialaccess.interfaces

import android.os.SystemClock
import android.util.Log
import android.view.View

class SafeClickListener(
        private var defaultInterval: Int = 1000,
        private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        Log.d("TAG", "onClick: ${SystemClock.elapsedRealtime() - lastTimeClicked}")
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}