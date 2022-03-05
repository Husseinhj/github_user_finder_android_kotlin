package com.github.husseinhj.githubuser.extensions

import android.annotation.SuppressLint
import com.github.husseinhj.githubuser.utils.AnyDebounce
import com.github.husseinhj.githubuser.utils.SubscribeOnDebounced
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String.convertToDate(): Date? {
    return SimpleDateFormat("yyyy-MM-dd").parse(this)
}

fun String?.debounce(subscribe: SubscribeOnDebounced) {
    if (this == null) {
        return subscribe.invoke(null)
    }

    AnyDebounce.searchDebounced(this, 300) {
        subscribe.invoke(it)
    }
}