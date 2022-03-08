package com.github.husseinhj.githubuser.models

import com.google.gson.annotations.SerializedName

data class UserSearchResponseModel (
    @SerializedName("total_count")
    val totalCount: Long? = null,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,
    val items: List<UserSimpleDetailsModel>? = null
)
