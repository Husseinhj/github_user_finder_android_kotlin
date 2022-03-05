package com.github.husseinhj.githubuser.views.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.github.husseinhj.githubuser.R
import androidx.databinding.DataBindingUtil
import com.github.husseinhj.githubuser.bases.BaseFragment
import com.github.husseinhj.githubuser.databinding.FragmentHomeBinding
import com.github.husseinhj.githubuser.extensions.showSoftBackButton
import com.github.husseinhj.githubuser.viewmodels.fragments.HomeViewModel

class HomeFragment : BaseFragment() {

    private val viewModel = HomeViewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        title = getString(R.string.home_title)
        this.toolbarAppearance?.setOnFocusListener {
            this.navigateFromHomeToSearchFragment()
        }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.callToActionButton.setOnClickListener {
            this.navigateFromHomeToSearchFragment()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        this.showSoftBackButton(false)
    }
}