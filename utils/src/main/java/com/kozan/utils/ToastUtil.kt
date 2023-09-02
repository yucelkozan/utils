package com.kozan.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun longToast(context: Context, text:String){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show()
    }

    fun shortToast(context: Context, text:String){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }


}