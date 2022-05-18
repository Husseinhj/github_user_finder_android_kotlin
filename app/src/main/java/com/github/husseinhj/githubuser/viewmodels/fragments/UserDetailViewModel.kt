package com.github.husseinhj.githubuser.viewmodels.fragments

import retrofit2.Call
import android.view.View
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.husseinhj.githubuser.extensions.toString
import com.github.husseinhj.githubuser.extensions.isoStringToDate
import com.github.husseinhj.githubuser.models.UserDetailsResponseModel
import com.github.husseinhj.githubuser.services.repositories.UserRepository

class UserDetailViewModel(
    private val state: SavedStateHandle,
    private val userRepository: UserRepository
): ViewModel() {

    val serverErrorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::serverErrorMessage.name])
    }
    val userAvatarUrl: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userAvatarUrl.name])
    }
    val fullName: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::fullName.name])
    }
    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::username.name])
    }
    val userBio: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userBio.name])
    }
    val userFollowers: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userFollowers.name])
    }
    val userFollowing: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userFollowing.name])
    }
    val userLocation: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userLocation.name])
    }
    val userEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userEmail.name])
    }
    val userCompany: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userCompany.name])
    }
    val userJoinedAt: MutableLiveData<String> by lazy {
        MutableLiveData<String>(state[::userJoinedAt.name])
    }
    val locationVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::locationVisibility.name] ?: View.GONE)
    }
    val companyVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::companyVisibility.name] ?: View.GONE)
    }
    val emailVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::emailVisibility.name] ?: View.GONE)
    }
    val loadingVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(state[::loadingVisibility.name] ?: View.GONE)
    }

    fun saveState() {
        state.apply {
            set(::fullName.name, fullName.value)
            set(::username.name, username.value)
            set(::userBio.name, userBio.value)
            set(::userEmail.name, userEmail.value)
            set(::userCompany.name, userCompany.value)
            set(::userJoinedAt.name, userJoinedAt.value)
            set(::userLocation.name, userLocation.value)
            set(::userAvatarUrl.name, userAvatarUrl.value)
            set(::userFollowers.name, userFollowers.value)
            set(::userFollowing.name, userFollowing.value)
            set(::emailVisibility.name, emailVisibility.value)
            set(::companyVisibility.name, companyVisibility.value)
            set(::loadingVisibility.name, loadingVisibility.value)
            set(::serverErrorMessage.name, serverErrorMessage.value)
            set(::locationVisibility.name, locationVisibility.value)
        }
    }

    fun getUserDetail(userId: String) {
        loadingVisibility.value = View.VISIBLE

        viewModelScope.launch {
            userRepository.getUserDetailAsync(userId, object: Callback<UserDetailsResponseModel> {
                override fun onResponse(
                    call: Call<UserDetailsResponseModel>,
                    response: Response<UserDetailsResponseModel>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            fullName.postValue(name)
                            username.postValue("@$login")
                            userBio.postValue(bio ?: "")
                            userFollowers.postValue(followers.toString())
                            userFollowing.postValue(following.toString())
                            userAvatarUrl.postValue(avatarURL.toString())

                            applyEmailState(email)
                            applyJointState(createdAt)
                            applyLocationState(location)
                            applyCompanyNameState(company)
                        }
                    }

                    loadingVisibility.postValue(View.GONE)
                }

                override fun onFailure(call: Call<UserDetailsResponseModel>, t: Throwable) {
                    loadingVisibility.postValue(View.GONE)
                    serverErrorMessage.postValue(t.localizedMessage ?: "Unknown error")
                }

            })
        }
    }

    private fun applyJointState(createdAt: String?) {
        val date = createdAt?.isoStringToDate()
        userJoinedAt.postValue(date?.toString("MMMM yyyy") ?: "")
    }

    private fun applyLocationState(location: String?) {
        userLocation.postValue(location)
        locationVisibility.postValue(if (location.isNullOrBlank()) View.GONE else View.VISIBLE)
    }

    private fun applyCompanyNameState(company: String?) {
        userCompany.postValue(company)
        companyVisibility.postValue(if (company.isNullOrBlank()) View.GONE else View.VISIBLE)
    }

    private fun applyEmailState(email: String?) {
        userEmail.postValue(email)
        emailVisibility.postValue(if (email.isNullOrBlank()) View.GONE else View.VISIBLE)
    }
}