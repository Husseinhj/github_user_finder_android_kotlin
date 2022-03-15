package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.github.husseinhj.githubuser.R
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.github.husseinhj.githubuser.bases.BaseFragment
import com.github.husseinhj.githubuser.databinding.FragmentHomeBinding
import com.github.husseinhj.githubuser.viewmodels.fragments.HomeViewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        this.enableBackButton(false)
        this.setTitle(getString(R.string.home_title))

        this.setOnSearchBarFocusListener {
            this.navigateFromHomeToSearchFragment()
        }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.callToActionButton.setOnClickListener {
            this.navigateFromHomeToSearchFragment()
        }

        return binding.root
    }
}