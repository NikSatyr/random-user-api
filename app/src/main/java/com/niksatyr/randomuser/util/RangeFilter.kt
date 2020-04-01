package com.niksatyr.randomuser.util

import android.text.InputFilter
import android.text.Spanned

class RangeFilter(val min: Int, val max: Int) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(min, max, input)) return source
        } catch (nfe: NumberFormatException) {
            return ""
        }
        return ""
    }

    private fun isInRange(min: Int, max: Int, value: Int): Boolean {
        return value in min..max
    }

}