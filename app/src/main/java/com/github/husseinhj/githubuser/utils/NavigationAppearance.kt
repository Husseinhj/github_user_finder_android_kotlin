package com.github.husseinhj.githubuser.utils

import androidx.appcompat.app.AppCompatActivity
import com.github.husseinhj.githubuser.extensions.showSoftBackButton

/**
 * A wrapper class that handles navigation bar title, back button
 */
class NavigationAppearance(private val activity: AppCompatActivity) {

    fun setShowBackSoftButton(show: Boolean) {
        activity.showSoftBackButton(show)
    }

    fun setTitle(title: String?) {
        activity.title = title
    }

}