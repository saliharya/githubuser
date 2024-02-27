package com.arya.githubuser.presentation.fragment

import android.os.Bundle
import com.arya.githubuser.presentation.viewmodel.FollowerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FollowerFragment : BaseFollowingFollowerFragment() {
    override val viewModel by viewModel<FollowerViewModel>()
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

