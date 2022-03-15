package com.github.husseinhj.githubuser.extensions

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showSoftBackButton(show: Boolean) {
    this.supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(show)
        setDisplayShowHomeEnabled(show)
    }
}