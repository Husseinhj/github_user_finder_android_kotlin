package com.github.husseinhj.githubuser.services.repositories.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.github.husseinhj.githubuser.models.data.UserSearchResponseModel

interface ISearchRepository {

    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<UserSearchResponseModel>

}