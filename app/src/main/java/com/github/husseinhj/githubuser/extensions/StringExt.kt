package com.github.husseinhj.githubuser.extensions

import java.util.*
import java.lang.Exception
import java.text.SimpleDateFormat
import android.annotation.SuppressLint
import com.github.husseinhj.githubuser.utils.AnyDebounce
import com.github.husseinhj.githubuser.utils.SubscribeOnDebounced

@SuppressLint("SimpleDateFormat")
fun String.isoStringToDate(): Date? {
    try {
        return SimpleDateFormat("yyyy-MM-dd").parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

fun String?.debounce(subscribe: SubscribeOnDebounced) {
    if (this == null) {
        return subscribe.invoke(null)
    }

    AnyDebounce.searchDebounced(this, 300) {
        subscribe.invoke(it)
    }
}