
package com.myapps.allsocialaccess.interfaces

import com.google.android.material.bottomsheet.BottomSheetDialog

interface AdapterOnClick {
    fun onClick(item: String)
    fun onClickLanguage(item: String, bottomSheetDialog: BottomSheetDialog,position:Int)
}