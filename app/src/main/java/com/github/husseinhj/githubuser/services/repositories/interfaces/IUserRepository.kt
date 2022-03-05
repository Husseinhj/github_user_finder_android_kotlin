package com.github.husseinhj.githubuser.services.repositories.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.github.husseinhj.githubuser.models.data.UserDetailsResponseModel

interface IUserRepository {

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailsResponseModel>

}