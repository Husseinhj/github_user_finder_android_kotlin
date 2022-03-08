package com.github.husseinhj.githubuser.models

import com.google.gson.annotations.SerializedName

data class UserDetailsResponseModel (
    val name: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val hireable: Boolean? = null,
    val bio: String? = null,
    @SerializedName("twitter_username")
    val twitterUsername: String? = null,
    @SerializedName("public_repos")
    val publicRepos: Long? = null,
    @SerializedName("public_gists")
    val publicGists: Long? = null,
    val followers: Long? = null,
    val following: Long? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
): UserSimpleDetailsModel()
