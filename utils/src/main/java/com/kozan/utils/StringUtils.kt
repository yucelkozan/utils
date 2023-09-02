package com.kozan.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import java.util.Locale
import java.util.Vector

object StringUtils {
    private fun sentenceToWordList(text: String?): List<String> {
        val list = text?.split("\\s+".toRegex())!!.map { word ->
            word.replace("""^[,\.]|[,\.]$""".toRegex(), "") }
        return list

    }


    fun trim(str: String?): String? {
        var str = str
        if (str != null) {
            str = str.trim { it <= ' ' }
        }
        return str
    }

    fun split(str: String, delimeter: String): Array<String?>? {
        var str = str
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(delimeter)) {
            return null
        }
        val vector = Vector<String>()
        var index = str.indexOf(delimeter)
        while (index >= 0) {
            vector.add(str.substring(0, index))
            str = str.substring(index + delimeter.length)
            index = str.indexOf(delimeter)
        }
        vector.add(str)
        val strArray = arrayOfNulls<String>(vector.size)
        if (vector.size > 0) {
            for (i in vector.indices) {
                strArray[i] = vector[i]
            }
        }
        return strArray
    }

    fun getCapitalize(str: String): String {
        if (TextUtils.isEmpty(str)) return ""
        return if (str.length > 0) {
            str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1)
                .lowercase(Locale.getDefault())
        } else str
    }

    fun copyTextToClipboard(context: Context, label: String?, text: String?) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }
}