package com.github.husseinhj.githubuser.services.repositories

import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import com.github.husseinhj.githubuser.models.data.UserSearchResponseModel
import com.github.husseinhj.githubuser.services.providers.WebServiceProvider
import com.github.husseinhj.githubuser.services.repositories.interfaces.ISearchRepository

object SearchRepository {
    private val searchService: ISearchRepository = WebServiceProvider.createService(ISearchRepository::class.java)

    fun searchUserAsync(query: String, callback: Callback<UserSearchResponseModel>) {
        val serviceCall = searchService.searchUser(query)
        serviceCall.enqueue(callback)
    }

    suspend fun searchUser(query: String): Response<UserSearchResponseModel> {
        val serviceCall = searchService.searchUser(query)

        return serviceCall.awaitResponse()
    }
}