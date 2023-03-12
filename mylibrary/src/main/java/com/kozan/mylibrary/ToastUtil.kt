package com.kozan.mylibrary

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun longToast(context: Context, text:String){
        Toast.makeText(context,text,Toast.LENGTH_LONG)
    }
}