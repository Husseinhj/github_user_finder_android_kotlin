package com.github.husseinhj.githubuser.viewmodels.fragments

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.husseinhj.githubuser.adapters.OnUserCellClickedListener
import com.github.husseinhj.githubuser.adapters.UserSearchResultAdapter
import com.github.husseinhj.githubuser.models.data.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.models.eventbus.OnSearchBarMessage
import com.github.husseinhj.githubuser.services.repositories.SearchRepository
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class SearchUserViewModel: ViewModel() {
    private lateinit var clickListener: OnUserCellClickedListener

    private var searchJob: Job? = null
    var dataset: List<UserSimpleDetailsModel>? = null

    val resultAdapter: MutableLiveData<UserSearchResultAdapter> by lazy {
        MutableLiveData<UserSearchResultAdapter>()
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

    private fun searchUser(query: String?) {
        searchJob?.cancel()

        if (query.isNullOrBlank()) {
            resultAdapter.value = null
            loadingVisibility.value = View.GONE
            emptyResultVisibility.value = View.VISIBLE
            return
        }

        val validatedSearchQuery = query
            .trimStart()
            .trimEnd()
            .lowercase(Locale.getDefault())

        loadingVisibility.value = View.VISIBLE
        emptyResultVisibility.value = View.GONE
        searchJob = CoroutineScope(Dispatchers.IO).launch {
            val result = SearchRepository.searchUser(validatedSearchQuery)
            withContext(Dispatchers.Main) {
                run {
                    loadingVisibility.value = View.GONE
                    dataset = result.body()?.items
                    if (result.isSuccessful) {
                        val adapter = UserSearchResultAdapter(dataset!!) {
                            clickListener.invoke(it)
                        }
                        resultAdapter.value = adapter

                        resultVisibility.value = View.VISIBLE
                        emptyResultVisibility.value = View.GONE
                    } else {
                        resultAdapter.value = null
                        resultVisibility.value = View.GONE
                        emptyResultVisibility.value = View.VISIBLE
                    }
                }
            }
        }
    }

    fun setOnUserCellClickedListener(listener: OnUserCellClickedListener) {
        this.clickListener = listener
    }

    fun subscribeOnSearchBarQueryEvent() {
        EventBus.getDefault().register(this)
    }

    fun unsubscribeToSearchBarQueryEvent() {
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onSearchBarMessage(message: OnSearchBarMessage) {
        if (message.focused != null) {
            return
        }

        searchUser(message.query)
    }
}