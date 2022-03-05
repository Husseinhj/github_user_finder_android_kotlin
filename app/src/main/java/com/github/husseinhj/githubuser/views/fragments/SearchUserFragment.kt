package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.databinding.FragmentSearchUserBinding
import com.github.husseinhj.githubuser.models.data.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.models.eventbus.OnSearchBarMessage
import com.github.husseinhj.githubuser.viewmodels.fragments.SearchUserViewModel
import org.greenrobot.eventbus.EventBus

class SearchUserFragment : Fragment() {

    private var viewModel = SearchUserViewModel()
    private lateinit var binding: FragmentSearchUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_user,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.subscribeOnSearchBarQueryEvent()
        viewModel.setOnUserCellClickedListener {
            navigateToUserDetail(it)
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()

        viewModel.unsubscribeToSearchBarQueryEvent()
    }

    private fun navigateToUserDetail(userModel: UserSimpleDetailsModel) {
        EventBus.getDefault().post(OnSearchBarMessage(focused = false))

        val data = Bundle()
        data.putString(GITHUB_USERNAME, userModel.login)

        NavHostFragment.findNavController(this).navigate(
            R.id.action_searchUserFragment_to_userDetailFragment,
            data
        )
    }
}