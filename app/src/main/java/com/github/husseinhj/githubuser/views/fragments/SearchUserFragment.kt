package com.github.husseinhj.githubuser.views.fragments

import android.media.metrics.Event
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.databinding.FragmentSearchUserBinding
import com.github.husseinhj.githubuser.extensions.dismissKeyboard
import com.github.husseinhj.githubuser.models.eventbus.OnSearchBarMessage
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchUserFragment : Fragment() {

    private var viewModel = SearchUserViewModel()
    private lateinit var binding: FragmentSearchUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        EventBus.getDefault().register(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_user,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setOnUserCellClickedListener {
            context?.dismissKeyboard()
            EventBus.getDefault().post(OnSearchBarMessage(focused = false))

            val data = Bundle()
            data.putString(GITHUB_USERNAME, it.login)

            NavHostFragment.findNavController(this).navigate(
                R.id.action_searchUserFragment_to_userDetailFragment,
                data
            )
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()

        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onSearchBarMessage(message: OnSearchBarMessage) {
        if (message.focused != null) {
            return
        }

        context?.let { safeContext -> viewModel.searchUser(message.query, safeContext) }
    }
}