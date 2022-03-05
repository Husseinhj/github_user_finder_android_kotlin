package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.databinding.FragmentUserDetailBinding
import com.github.husseinhj.githubuser.models.eventbus.OnBackButtonVisibilityMessage
import com.github.husseinhj.githubuser.viewmodels.fragments.UserDetailViewModel
import org.greenrobot.eventbus.EventBus

const val GITHUB_USERNAME: String = "github_username"

class UserDetailFragment : Fragment() {
    private var usernameParam: String? = null
    private var viewModel = UserDetailViewModel()
    private lateinit var binding: FragmentUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usernameParam = it.getString(GITHUB_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_detail,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        usernameParam?.let { viewModel.getUserDetail(it, binding.userAvatarView, requireContext()) }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        EventBus.getDefault().post(OnBackButtonVisibilityMessage(true))
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().post(OnBackButtonVisibilityMessage(false))
    }
}