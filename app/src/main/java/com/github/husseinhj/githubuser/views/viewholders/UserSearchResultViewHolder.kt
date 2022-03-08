package com.github.husseinhj.githubuser.views.viewholders

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.extensions.downloadAndShowImage

class UserSearchResultViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.image_view)
    private val usernameView: TextView = itemView.findViewById(R.id.username_view)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setImageUrl(imageUrl: String) {
        imageView.downloadAndShowImage(imageUrl)
    }

    fun setUsername(username: String) {
        usernameView.text = username
    }
}