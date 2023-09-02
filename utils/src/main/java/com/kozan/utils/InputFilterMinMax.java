package com.kozan.utils;

import android.text.InputFilter;
import android.text.Spanned;

/***
 * This class restricts min and max values for edittext.
 * For example, with new InputFilterMinMax(0,29) you can enter only 0-29 values for edittext.
 */
public class InputFilterMinMax implements InputFilter {

    private int min, max;
    private int digitNumberMin, digitNumberMax;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
        setDigitNumbers();
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
        setDigitNumbers();
    }

    private void setDigitNumbers(){
        digitNumberMin = String.valueOf(min).length();
        digitNumberMax = String.valueOf(max).length();
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            int digitNumberInput = String.valueOf(input).length();
            if(digitNumberInput < digitNumberMin) return null;
            else if(digitNumberInput < digitNumberMax) return null;
            else{
                if (isInRange(min, max, input))
                    return null;
            }
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
