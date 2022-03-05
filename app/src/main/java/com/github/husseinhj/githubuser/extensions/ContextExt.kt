package com.github.husseinhj.githubuser.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Context.dismissKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val windowToken = (this as? Activity)?.currentFocus?.windowToken
    windowToken?.let { imm.hideSoftInputFromWindow(it, 0) }
}