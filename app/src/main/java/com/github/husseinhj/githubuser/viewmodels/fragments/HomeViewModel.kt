package com.github.husseinhj.githubuser.viewmodels.fragments

import androidx.lifecycle.ViewModel
import com.github.husseinhj.githubuser.models.eventbus.OnSearchBarMessage
import org.greenrobot.eventbus.EventBus

class HomeViewModel: ViewModel() {

    fun onStartButtonClicked() {
        EventBus.getDefault().post(OnSearchBarMessage(focused = true))
    }

}