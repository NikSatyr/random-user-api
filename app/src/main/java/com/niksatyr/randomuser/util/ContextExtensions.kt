package com.niksatyr.randomuser.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.createToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this, message, duration)