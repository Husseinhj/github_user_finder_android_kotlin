package com.github.husseinhj.githubuser.viewmodels.fragments

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.husseinhj.githubuser.extensions.convertToDate
import com.github.husseinhj.githubuser.extensions.downloadAndShowImage
import com.github.husseinhj.githubuser.extensions.toString
import com.github.husseinhj.githubuser.models.data.UserDetailsResponseModel
import com.github.husseinhj.githubuser.services.repositories.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel: ViewModel() {

    val fullName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val username: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userBio: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userFollowers: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userFollowing: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userLocation: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userCompany: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userJoinedAt: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val locationVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val companyVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val emailVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val loadingVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun getUserDetail(userId: String, userAvatarView: ImageView) {
        loadingVisibility.value = View.VISIBLE

        UserRepository.getUserDetailAsync(userId, object: Callback<UserDetailsResponseModel> {
            override fun onResponse(
                call: Call<UserDetailsResponseModel>,
                response: Response<UserDetailsResponseModel>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        fullName.value = name
                        username.value = "@$login"
                        userBio.value = bio ?: ""
                        userFollowers.value = followers.toString()
                        userFollowing.value = following.toString()
                        applyImageToView(avatarURL, userAvatarView)

                        val date = createdAt?.convertToDate()
                        userJoinedAt.value = date?.toString("MMMM YYYY") ?: ""

                        userLocation.value = location
                        locationVisibility.value = if (location.isNullOrBlank()) View.GONE else View.VISIBLE

                        userCompany.value = company
                        companyVisibility.value = if (company.isNullOrBlank()) View.GONE else View.VISIBLE

                        userEmail.value = email
                        emailVisibility.value = if (email.isNullOrBlank()) View.GONE else View.VISIBLE
                    }
                }

                loadingVisibility.value = View.GONE
            }

            override fun onFailure(call: Call<UserDetailsResponseModel>, t: Throwable) {
                loadingVisibility.value = View.GONE
            }

        })
    }

    fun applyImageToView(imageUrl: String?, view: ImageView) {
        view.downloadAndShowImage(imageUrl)
    }
}