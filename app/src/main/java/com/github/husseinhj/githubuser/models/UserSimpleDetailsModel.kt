package com.github.husseinhj.githubuser.models

import com.google.gson.annotations.SerializedName

open class UserSimpleDetailsModel (
    val login: String? = null,
    val id: Long? = null,
    @SerializedName("node_id")
    val nodeID: String? = null,
    @SerializedName("avatar_url")
    val avatarURL: String? = null,
    @SerializedName("gravatar_id")
    val gravatarID: String? = null,
    val url: String? = null,
    @SerializedName("html_url")
    val htmlURL: String? = null,
    @SerializedName("followers_url")
    val followersURL: String? = null,
    @SerializedName("following_url")
    val followingURL: String? = null,
    @SerializedName("gists_url")
    val gistsURL: String? = null,
    @SerializedName("starred_url")
    val starredURL: String? = null,
    @SerializedName("subscriptions_url")
    val subscriptionsURL: String? = null,
    @SerializedName("organizations_url")
    val organizationsURL: String? = null,
    @SerializedName("repos_url")
    val reposURL: String? = null,
    @SerializedName("events_url")
    val eventsURL: String? = null,
    @SerializedName("received_events_url")
    val receivedEventsURL: String? = null,
    val type: String? = null,
    @SerializedName("site_admin")
    val siteAdmin: Boolean? = null,
    val score: Long? = null
)
