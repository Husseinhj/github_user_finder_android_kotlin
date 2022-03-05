package com.github.husseinhj.githubuser.viewmodels.fragments

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.husseinhj.githubuser.adapters.OnUserCellClickedListener
import com.github.husseinhj.githubuser.adapters.UserSearchResultAdapter
import com.github.husseinhj.githubuser.models.data.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.services.repositories.SearchRepository
import com.github.husseinhj.githubuser.utils.InternetConnectivityUtil
import kotlinx.coroutines.*
import java.util.*

enum class ErrorEnumType {
    NONE,
    NETWORK,
    SERVER
}

class SearchUserViewModel: ViewModel() {
    private lateinit var clickListener: OnUserCellClickedListener

    private var searchJob: Job? = null
    var dataset: List<UserSimpleDetailsModel>? = null

    val resultAdapter: MutableLiveData<UserSearchResultAdapter> by lazy {
        MutableLiveData<UserSearchResultAdapter>()
    }

    val errorType: MutableLiveData<ErrorEnumType> by lazy {
        MutableLiveData<ErrorEnumType>()
    }

    val loadingVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(View.GONE)
    }

    val emptyResultVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(View.VISIBLE)
    }

    val resultVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(View.GONE)
    }

    val errorPlaceholderVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(View.GONE)
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
        searchJob = CoroutineScope(Dispatchers.IO).launch {
            val result = SearchRepository.searchUser(validatedSearchQuery)
            withContext(Dispatchers.Main) {
                run {
                    loadingVisibility.value = View.GONE
                    dataset = result.body()?.items
                    if (!result.isSuccessful) {
                        showNowValuePlaceholder()
                        errorType.value = ErrorEnumType.SERVER
                        errorPlaceholderVisibility.value = View.VISIBLE

                        return@run
                    }

                    val adapter = UserSearchResultAdapter(dataset!!) {
                        clickListener.invoke(it)
                    }
                    resultAdapter.value = adapter

                    errorType.value = ErrorEnumType.NONE
                    resultVisibility.value = View.VISIBLE
                    emptyResultVisibility.value = View.GONE
                    errorPlaceholderVisibility.value = View.GONE
                }
            }
        }
    }

    private fun showNowValuePlaceholder() {
        resultAdapter.value = null
        loadingVisibility.value = View.GONE
        emptyResultVisibility.value = View.VISIBLE
    }

    private fun removeSpaceAndLowercase(query: String) = query
        .trimStart()
        .trimEnd()
        .lowercase(Locale.getDefault())

    fun setOnUserCellClickedListener(listener: OnUserCellClickedListener) {
        this.clickListener = listener
    }

}