package com.kozan.utils

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Arrays
import java.util.TreeSet

object SharedPrefUtils {
    // String
    fun getString(
        context: Context?,
        prefName: String?,
        key: String?,
        defaultValue: String?
    ): String? {
        if (context == null) return defaultValue
        val settings = context.getSharedPreferences(prefName, 0)
        return settings.getString(key, defaultValue)
    }

    fun setString(context: Context?, prefName: String?, key: String?, value: String?) {
        if (context == null) return
        val settings = context.getSharedPreferences(prefName, 0)
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    // Int
    fun getInt(context: Context?, prefName: String?, key: String?, defaultValue: Int): Int {
        if (context == null) return defaultValue
        val settings = context.getSharedPreferences(prefName, 0)
        return settings.getInt(key, defaultValue)
    }

    fun setInt(context: Context?, prefName: String?, key: String?, value: Int) {
        if (context == null) return
        val settings = context.getSharedPreferences(prefName, 0)
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    // long
    fun getLong(context: Context?, prefName: String?, key: String?, defaultValue: Long): Long {
        if (context == null) return defaultValue
        val settings = context.getSharedPreferences(prefName, 0)
        return settings.getLong(key, defaultValue)
    }

    fun setLong(context: Context?, prefName: String?, key: String?, value: Long) {
        if (context == null) return
        val settings = context.getSharedPreferences(prefName, 0)
        val editor = settings.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    // Float
    fun getFloat(context: Context?, prefName: String?, key: String?, defaultValue: Float): Float {
        if (context == null) return defaultValue
        val settings = context.getSharedPreferences(prefName, 0)
        return settings.getFloat(key, defaultValue)
    }

    fun setFloat(context: Context?, prefName: String?, key: String?, value: Float) {
        if (context == null) return
        val settings = context.getSharedPreferences(prefName, 0)
        val editor = settings.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    // Boolean
    fun getBoolean(
        context: Context?,
        prefName: String?,
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        if (context == null) return defaultValue
        val settings = context.getSharedPreferences(prefName, 0)
        return settings.getBoolean(key, defaultValue)
    }

    fun setBoolean(context: Context?, prefName: String?, key: String?, value: Boolean) {
        if (context == null) return
        val settings = context.getSharedPreferences(prefName, 0)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    // Object
    fun <T> getObject(
        context: Context?,
        prefName: String?,
        key: String?,
        defaultObject: T,
        type: Type?
    ): T {
        if (context == null) return defaultObject
        val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString(key, "")
        return if (TextUtils.isEmpty(json)) {
            defaultObject
        } else {
            gson.fromJson(json, type)
        }
    }

    fun <T> setObject(context: Context?, prefName: String?, key: String?, `object`: T?) {
        if (context == null) return
        if (`object` == null) return
        val gson = Gson()
        val json = gson.toJson(`object`)
        setString(context, prefName, key, json)
    }

    // TreeSet Generic
    fun <T> getHashSet(
        context: Context?,
        prefName: String?,
        key: String?,
        defaultObject: TreeSet<T>,
        type: Type?
    ): TreeSet<T> {
        if (context == null) return defaultObject
        val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString(key, "")
        return if (TextUtils.isEmpty(json)) {
            defaultObject
        } else {
            gson.fromJson(json, type)
        }
    }

    fun <T> setHashSet(context: Context?, prefName: String?, key: String?, treeSet: TreeSet<T>?) {
        if (context == null) return
        val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(treeSet)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
    }

    // Arraylist Generic
    fun <T> getList(
        context: Context?,
        prefName: String?,
        key: String?,
        clazz: Class<Array<T>>?
    ): List<T> {
        if (context == null) return ArrayList()
        val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString(key, "")
        return if (TextUtils.isEmpty(json)) {
            ArrayList()
        } else {
            val arr = gson.fromJson(json, clazz)
            Arrays.asList(*arr)
        }
    }

    fun <T> getList(context: Context?, prefName: String?, key: String?, type: Type?): List<T> {
        if (context == null) return ArrayList()
        val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString(key, "")
        return if (TextUtils.isEmpty(json)) {
            ArrayList()
        } else {
            gson.fromJson(json, type)
        }
    }

    fun <T> setArrayList(
        context: Context?,
        prefName: String?,
        key: String?,
        arrayList: ArrayList<T>?
    ) {
        if (context == null) return
        val gson = Gson()
        val json = gson.toJson(arrayList)
        setString(context, prefName, key, json)
    }

    // HashMap ArrayList Generic
    fun <T> getArrayListHashMap(
        context: Context?,
        prefName: String?,
        key: String?,
        type: Type?
    ): HashMap<Long, ArrayList<T>> {
        if (context == null) return HashMap()
        val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val json = mPrefs.getString(key, "")
        return if (TextUtils.isEmpty(json)) {
            HashMap()
        } else {
            val gson = Gson()
            gson.fromJson(json, type)
        }
    }

    fun <T> setArrayListHashMap(
        context: Context?,
        prefName: String?,
        key: String?,
        hashMap: HashMap<Long?, ArrayList<T>?>?
    ) {
        if (context == null) return
        val gson = Gson()
        val json = gson.toJson(hashMap)
        setString(context, prefName, key, json)
    }

    // HashMap Generic
    fun <T> getObjectHashMap(
        context: Context?,
        prefName: String?,
        key: String?,
        type: Type?
    ): HashMap<Long, T> {
        if (context == null) return HashMap()
        val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val json = mPrefs.getString(key, "")
        return if (TextUtils.isEmpty(json)) {
            HashMap()
        } else {
            val gson = Gson()
            gson.fromJson(json, type)
        }
    }

    fun <T> setObjectHashMap(
        context: Context?,
        prefName: String?,
        key: String?,
        hashMap: HashMap<Long?, T>?
    ) {
        if (context == null) return
        val gson = Gson()
        val json = gson.toJson(hashMap)
        setString(context, prefName, key, json)
    }

    // HashMap Integer
    fun getIntegerHashMap(
        context: Context?,
        prefName: String?,
        key: String?
    ): HashMap<Long?, Int?> {
        var hashMap = HashMap<Long?, Int?>()
        if (context != null) {
            val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val storedHashMapString = mPrefs.getString(key, "")
            hashMap = if (!TextUtils.isEmpty(storedHashMapString)) {
                val gson = Gson()
                val type = object : TypeToken<HashMap<Long?, Int?>?>() {}.type
                gson.fromJson(storedHashMapString, type)
            } else {
                HashMap()
            }
        }
        return hashMap
    }

    fun setIntegerHashMap(
        context: Context?,
        prefName: String?,
        key: String?,
        hashMap: HashMap<Long?, Int?>?
    ) {
        val gson = Gson()
        val hashMapString = gson.toJson(hashMap)
        setString(context, prefName, key, hashMapString)
    }

    // HashMap Boolean
    fun getBooleanHashMap(
        context: Context?,
        prefName: String?,
        key: String?
    ): HashMap<Long?, Boolean?> {
        var hashMap = HashMap<Long?, Boolean?>()
        if (context != null) {
            val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val storedHashMapString = mPrefs.getString(key, "")
            hashMap = if (!TextUtils.isEmpty(storedHashMapString)) {
                val gson = Gson()
                val type = object : TypeToken<HashMap<Long?, Boolean?>?>() {}.type
                gson.fromJson(storedHashMapString, type)
            } else {
                HashMap()
            }
        }
        return hashMap
    }

    fun setBooleanHashMap(
        context: Context?,
        prefName: String?,
        key: String?,
        hashMap: HashMap<Long?, Boolean?>?
    ) {
        val gson = Gson()
        val hashMapString = gson.toJson(hashMap)
        setString(context, prefName, key, hashMapString)
    }

    // HashMap String
    fun getStringHashMap(
        context: Context?,
        prefName: String?,
        key: String?
    ): HashMap<Long?, String?> {
        var hashMap = HashMap<Long?, String?>()
        if (context != null) {
            val mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val storedHashMapString = mPrefs.getString(key, "")
            hashMap = if (!TextUtils.isEmpty(storedHashMapString)) {
                val gson = Gson()
                val type = object : TypeToken<HashMap<Long?, String?>?>() {}.type
                gson.fromJson(storedHashMapString, type)
            } else {
                HashMap()
            }
        }
        return hashMap
    }

    fun setStringHashMap(
        context: Context?,
        prefName: String?,
        key: String?,
        hashMap: HashMap<Long?, String?>?
    ) {
        val gson = Gson()
        val hashMapString = gson.toJson(hashMap)
        setString(context, prefName, key, hashMapString)
    }
}