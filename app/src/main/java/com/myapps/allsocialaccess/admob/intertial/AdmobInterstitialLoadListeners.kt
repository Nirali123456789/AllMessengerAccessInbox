package com.myapps.allsocialaccess.admob.intertial

interface AdmobInterstitialLoadListeners {
    fun adLoaded()
    fun adFailed()
    fun adAlreadyLoaded()
    fun adIsLoading()

}