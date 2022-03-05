package com.github.husseinhj.githubuser.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment


fun Fragment.navigate(to: Int, data: Bundle? = null) {
    NavHostFragment.findNavController(this)
        .navigate(to, data)
}

fun Fragment.navigateUp() {
    NavHostFragment.findNavController(this)
        .navigateUp()
}