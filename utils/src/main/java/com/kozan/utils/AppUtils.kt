package com.kozan.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AppUtils {

    fun <T>readJsonInAssets(context: Context, fileName:String, modelClass : Class<T> ) : List<T>{
        val jsonFileString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<T>>(){}.type
        val list : List<T> = Gson().fromJson(jsonFileString, type)
        return list

    }
}