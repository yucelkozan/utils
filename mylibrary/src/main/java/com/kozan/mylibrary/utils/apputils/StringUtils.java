package com.kozan.mylibrary.utils.apputils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;


import java.util.Vector;

public class StringUtils {
    public static String trim(String str) {
        if (str != null) {
            str = str.trim();
        }

        return str;
    }

    public static String[] split(String str, String delimeter) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(delimeter)) {
            return null;
        }

        Vector<String> vector = new Vector<>();

        int index = str.indexOf(delimeter);
        while (index >= 0) {
            vector.add(str.substring(0, index));
            str = str.substring(index + delimeter.length());
            index = str.indexOf(delimeter);
        }

        vector.add(str);

        String[] strArray = new String[vector.size()];
        if (vector.size() > 0) {
            for (int i = 0; i < vector.size(); i++) {
                strArray[i] = vector.get(i);

            }
        }

        return strArray;
    }

    public static String getCapitalize(String str) {
        if (TextUtils.isEmpty(str)) return "";
        if (str.length() > 0) {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
        return str;
    }

    public static void copyTextToClipboard(Context context, String label, String text){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }
}
