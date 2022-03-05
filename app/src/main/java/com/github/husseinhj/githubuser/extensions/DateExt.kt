package com.github.husseinhj.githubuser.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toString(format: String): String? {
    return SimpleDateFormat(format).format(this)
}