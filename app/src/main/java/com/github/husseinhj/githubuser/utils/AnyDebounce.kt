package com.github.husseinhj.githubuser.utils

import com.github.husseinhj.githubuser.consts.DEBOUNCE_FOR_SEARCH
import kotlinx.coroutines.*

typealias SubscribeOnDebounced = (String?) -> Unit
object AnyDebounce {
    private var searchJob: Job? = null

    fun searchDebounced(searchText: String, delay: Long = DEBOUNCE_FOR_SEARCH, debounced: SubscribeOnDebounced) {
        searchJob?.cancel()
        searchJob = CoroutineScope(Dispatchers.Main).launch {
            delay(delay)
            debounced.invoke(searchText)
        }
    }
}