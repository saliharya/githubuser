package com.arya.githubuser.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.arya.githubuser.presentation.viewmodel.FollowerViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FollowerFragment : BaseFollowingFollowerFragment() {
    override val viewModel by viewModels<FollowerViewModel>()
    override fun fetchData(username: String) {
        viewModel.fetchData(username)
    }

    companion object {
        fun newInstance(username: String): FollowerFragment {
            val args = Bundle()
            args.putString("username", username)
            val fragment = FollowerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

