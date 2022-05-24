package com.github.husseinhj.githubuser.viewmodels.fragments

import java.util.*
import android.view.View
import com.google.gson.Gson
import kotlinx.coroutines.*
//import android.content.Context
import androidx.lifecycle.*
import com.github.husseinhj.githubuser.models.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.utils.InternetConnectivityUtil
import com.github.husseinhj.githubuser.adapters.UserSearchResultAdapter
import com.github.husseinhj.githubuser.services.repositories.SearchRepository

enum class ErrorEnumType {
    NETWORK,
    SERVER
}

class SearchUserViewModel(private val searchRepository: SearchRepository): ViewModel() {

    private var searchJob: Job? = null
    private val viewModelState = MutableLiveData<SearchUserViewModelState>()

    val data: LiveData<SearchUserViewModelState>
        get() = viewModelState

    init {
        viewModelState.value = SearchUserViewModelState.NotSearchedYet
    }

    fun searchUser(query: String?) {
        searchJob?.cancel()

        if (query.isNullOrBlank()) {
            viewModelState.value = SearchUserViewModelState.NotSearchedYet
            return
        }

        val validatedSearchQuery = removeSpaceAndLowercase(query)
        viewModelState.value = SearchUserViewModelState.Loading

        searchJob = viewModelScope.launch {
            val result = searchRepository.searchUser(validatedSearchQuery)

            val userList = result.body()?.items
            if (!result.isSuccessful) {
                val errorType = if (result.code() == 502)
                    ErrorEnumType.NETWORK
                else
                    ErrorEnumType.SERVER

                viewModelState.postValue(
                    SearchUserViewModelState.Error(result.message(), errorType)
                )
                return@launch
            }

            if (userList.isNullOrEmpty()) {
                viewModelState.postValue(SearchUserViewModelState.NotFound)
                return@launch
            }

            viewModelState.postValue(
                SearchUserViewModelState.SearchedResult(userList)
            )
        }
    }

    private fun removeSpaceAndLowercase(query: String) = query
        .trimStart()
        .trimEnd()
        .lowercase(Locale.getDefault())

}

sealed class SearchUserViewModelState {
    object Loading: SearchUserViewModelState()
    object NotFound: SearchUserViewModelState()
    object NotSearchedYet: SearchUserViewModelState()
    data class SearchedResult(val dataset: List<UserSimpleDetailsModel>):
        SearchUserViewModelState()
    data class Error(val message: String? = null, val errorType: ErrorEnumType? = null):
        SearchUserViewModelState()
}