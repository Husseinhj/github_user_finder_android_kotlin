package com.github.husseinhj.githubuser.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.husseinhj.githubuser.R

fun ImageView.downloadAndShowImage(imageUrl: String?) {
    Glide
        .with(context)
        .load(imageUrl)
        .circleCrop()
        .error(R.drawable.ic_fail_profile_avatar_placeholder)
        .placeholder(R.drawable.ic_person_placeholder)
        .into(this)
}