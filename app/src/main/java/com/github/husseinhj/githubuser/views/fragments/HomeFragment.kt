package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.databinding.FragmentHomeBinding
import com.github.husseinhj.githubuser.extensions.navigate
import com.github.husseinhj.githubuser.models.eventbus.OnBackButtonVisibilityMessage
import com.github.husseinhj.githubuser.models.eventbus.OnSearchBarMessage
import com.github.husseinhj.githubuser.viewmodels.fragments.HomeViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class HomeFragment : Fragment() {

    private val viewModel =  HomeViewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.callToActionButton.setOnClickListener {
            viewModel.onStartButtonClicked()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        EventBus.getDefault().register(this)
        EventBus.getDefault().post(OnBackButtonVisibilityMessage(false))
    }

    override fun onStop() {
        super.onStop()

        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onSearchBarMessage(message: OnSearchBarMessage) {
        if (message.focused == true) {
            this.navigate(
                R.id.action_homeFragment_to_searchUserFragment
            )
        }
    }
}