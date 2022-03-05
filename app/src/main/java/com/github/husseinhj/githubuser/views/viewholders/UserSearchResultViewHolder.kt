package com.github.husseinhj.githubuser.views.viewholders

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.husseinhj.githubuser.R

class UserSearchResultViewHolder(itemView: View, private var context: Context)
    : RecyclerView.ViewHolder(itemView) {
    val userCellView: View = itemView.findViewById(R.id.user_cell_view)
    private val imageView: ImageView = itemView.findViewById(R.id.image_view)
    private val usernameView: TextView = itemView.findViewById(R.id.username_view)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setImageUrl(imageUrl: String) {
        Glide
            .with(context)
            .load(imageUrl)
            .circleCrop()
            .error(R.drawable.ic_fail_profile_avatar_placeholder)
            .placeholder(R.drawable.ic_person_placeholder)
            .into(imageView)
    }

    fun setUsername(username: String) {
        usernameView.text = username
    }
}