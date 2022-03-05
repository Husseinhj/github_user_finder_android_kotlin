package com.github.husseinhj.githubuser.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.github.husseinhj.githubuser.views.activities.MainActivity

fun Fragment.navigate(to: Int, data: Bundle? = null) {
    NavHostFragment.findNavController(this)
        .navigate(to, data)
}

fun Fragment.navigateUp() {
    NavHostFragment.findNavController(this)
        .navigateUp()
}

fun Fragment.navigate(to: Int, data: Bundle? = null, title: String? = null, showSoftBackButton: Boolean? = null) {
    NavHostFragment.findNavController(this)
        .navigate(to, data)

    val toolbarAppearance = (this.activity as? MainActivity)?.toolbarAppearance
    toolbarAppearance?.setTitle(title)
    toolbarAppearance?.setShowBackSoftButton(showSoftBackButton ?: false)
}

fun Fragment.showSoftBackButton(show: Boolean) {
    this.getAppCompatActivity().showSoftBackButton(show)
}

fun Fragment.getAppCompatActivity(): AppCompatActivity {
    return this.requireActivity() as AppCompatActivity
}