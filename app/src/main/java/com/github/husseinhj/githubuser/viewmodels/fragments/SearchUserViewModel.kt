package com.github.husseinhj.githubuser.viewmodels.fragments

import java.util.*
import android.view.View
import com.google.gson.Gson
import kotlinx.coroutines.*
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.husseinhj.githubuser.models.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.utils.InternetConnectivityUtil
import com.github.husseinhj.githubuser.adapters.UserSearchResultAdapter
import com.github.husseinhj.githubuser.services.repositories.SearchRepository

enum class ErrorEnumType {
    NONE,
    NETWORK,
    SERVER
}

class SearchUserViewModel(
    private val state: SavedStateHandle,
    private val searchRepository: SearchRepository
    ): ViewModel() {

    private var searchJob: Job? = null
    var dataset: List<UserSimpleDetailsModel>? = null

    val resultAdapter: MutableLiveData<UserSearchResultAdapter> by lazy {
        MutableLiveData<UserSearchResultAdapter>(getAdapterFromState())
    }

    val errorType: MutableLiveData<ErrorEnumType> by lazy {
        MutableLiveData<ErrorEnumType>(state[::errorType.name] ?: ErrorEnumType.NONE)
    }

    val loadingVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::loadingVisibility.name] ?: View.GONE)
    }

    val emptyResultVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::emptyResultVisibility.name] ?: View.VISIBLE)
    }

    val resultVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::resultVisibility.name] ?: View.GONE)
    }

    val errorPlaceholderVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::errorPlaceholderVisibility.name] ?: View.GONE)
    }

    private fun getAdapterFromState(): UserSearchResultAdapter? {
        val jsonDataSet: String? = state[::dataset.name]
        val rawList = Gson().fromJson(jsonDataSet, List::class.java)

        if (rawList?.firstOrNull() is UserSimpleDetailsModel) {
            @Suppress("UNCHECKED_CAST")
            val savedDataSet: List<UserSimpleDetailsModel>? = rawList as? List<UserSimpleDetailsModel>
            return getAdapterFromDataset(savedDataSet)
        }

        return null
    }

    private fun getAdapterFromDataset(dataset: List<UserSimpleDetailsModel>?): UserSearchResultAdapter? {
        if (dataset == null) {
            return null
        }

        return UserSearchResultAdapter(dataset)
    }

    fun saveState() {
        state.apply {
            set(::errorType.name, errorType.value)
            set(::dataset.name, Gson().toJson(dataset))
            set(::resultVisibility.name, resultVisibility.value)
            set(::loadingVisibility.name, loadingVisibility.value)
            set(::emptyResultVisibility.name, emptyResultVisibility.value)
            set(::errorPlaceholderVisibility.name, errorPlaceholderVisibility.value)
        }
    }

    fun searchUser(query: String?, context: Context) {
        searchJob?.cancel()

        if (query.isNullOrBlank()) {
            showNowValuePlaceholder()
            return
        }

        if (!InternetConnectivityUtil.isInternetAvailable(context)) {
            errorPlaceholderVisibility.value = View.VISIBLE
            errorType.value = ErrorEnumType.NETWORK
            return
        }

        errorType.value = ErrorEnumType.NONE
        val validatedSearchQuery = removeSpaceAndLowercase(query)

        loadingVisibility.value = View.VISIBLE
        emptyResultVisibility.value = View.GONE

        searchJob = viewModelScope.launch {
            val result = searchRepository.searchUser(validatedSearchQuery)
            loadingVisibility.postValue(View.GONE)
            dataset = result.body()?.items
            if (!result.isSuccessful) {
                showNowValuePlaceholder()
                errorType.postValue(ErrorEnumType.SERVER)
                errorPlaceholderVisibility.postValue(View.VISIBLE)
                return@launch
            }

            if (dataset.isNullOrEmpty()) {
                showNowValuePlaceholder()
                return@launch
            }

            val adapter = UserSearchResultAdapter(dataset!!)
            resultAdapter.postValue(adapter)

            errorType.postValue(ErrorEnumType.NONE)
            resultVisibility.postValue(View.VISIBLE)
            emptyResultVisibility.postValue(View.GONE)
            errorPlaceholderVisibility.postValue(View.GONE)
        }
    }

    private fun showNowValuePlaceholder() {
        resultAdapter.postValue(null)
        loadingVisibility.postValue(View.GONE)
        emptyResultVisibility.postValue(View.VISIBLE)
    }

    private fun removeSpaceAndLowercase(query: String) = query
        .trimStart()
        .trimEnd()
        .lowercase(Locale.getDefault())

}