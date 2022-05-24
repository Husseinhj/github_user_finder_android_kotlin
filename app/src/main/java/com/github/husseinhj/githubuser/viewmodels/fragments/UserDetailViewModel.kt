package com.github.husseinhj.githubuser.viewmodels.fragments

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.github.husseinhj.githubuser.models.UserDetailsResponseModel
import com.github.husseinhj.githubuser.services.repositories.UserRepository

class UserDetailViewModel(private val userRepository: UserRepository): ViewModel() {

    private val viewModelState = MutableLiveData<UserDetailViewModelState>()
    val data: LiveData<UserDetailViewModelState>
        get() = viewModelState

    init {
        viewModelState.value = UserDetailViewModelState.Loading
    }

    fun getUserDetail(userId: String) {
        viewModelState.value = UserDetailViewModelState.Loading

        viewModelScope.launch {
            userRepository.getUserDetailAsync(userId, object : Callback<UserDetailsResponseModel> {
                override fun onResponse(
                    call: Call<UserDetailsResponseModel>,
                    response: Response<UserDetailsResponseModel>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            viewModelState.postValue(UserDetailViewModelState.Data(it))
                        }
                    }
                }

                override fun onFailure(call: Call<UserDetailsResponseModel>, t: Throwable) {
                    viewModelState.postValue(
                        UserDetailViewModelState.Error(t.localizedMessage ?: "Unknown error")
                    )
                }

            })
        }
    }
}

sealed class UserDetailViewModelState {
    object Loading : UserDetailViewModelState()
    data class Data(
        val userDetail: UserDetailsResponseModel
    ) : UserDetailViewModelState()
    data class Error(val message: String) : UserDetailViewModelState()
}