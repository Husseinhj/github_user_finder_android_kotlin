package com.github.husseinhj.githubuser.services.repositories

import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import com.github.husseinhj.githubuser.models.UserDetailsResponseModel
import com.github.husseinhj.githubuser.services.repositories.interfaces.IUserRepository

class UserRepository(private val userRepoService: IUserRepository) {

    fun getUserDetailAsync(username: String, callback: Callback<UserDetailsResponseModel>) {
        val serviceCall = userRepoService.getUserDetail(username)
        serviceCall.enqueue(callback)
    }

    suspend fun getUserDetail(username: String): Response<UserDetailsResponseModel> {
        val serviceCall = userRepoService.getUserDetail(username)

        return serviceCall.awaitResponse()
    }
}