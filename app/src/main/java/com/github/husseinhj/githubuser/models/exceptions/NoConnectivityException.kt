package com.github.husseinhj.githubuser.models.exceptions

import java.io.IOException
import android.content.Context
import com.github.husseinhj.githubuser.R

class NoInternetException(val context: Context) : IOException() {
    val code: Int
        get() = 502

    override val message: String
        get() =
            context.getString(R.string.you_are_not_connected_to_internet)
}