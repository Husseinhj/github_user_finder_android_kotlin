package com.github.husseinhj.githubuser.services.repositories

import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import com.github.husseinhj.githubuser.models.UserSearchResponseModel
import com.github.husseinhj.githubuser.services.repositories.interfaces.ISearchRepository

class SearchRepository(private val searchService: ISearchRepository) {

    fun searchUserAsync(query: String, callback: Callback<UserSearchResponseModel>) {
        val serviceCall = searchService.searchUser(query)
        serviceCall.enqueue(callback)
    }

    suspend fun searchUser(query: String): Response<UserSearchResponseModel> {
        val serviceCall = searchService.searchUser(query)

        return serviceCall.awaitResponse()
    }
}