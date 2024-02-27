package com.arya.githubuser.presentation.fragment

import android.os.Bundle
import com.arya.githubuser.presentation.viewmodel.FollowingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : BaseFollowingFollowerFragment() {
    override val viewModel by viewModel<FollowingViewModel>()

    override fun fetchData(username: String) {
        viewModel.fetchData(username)
    }

    companion object {
        fun newInstance(username: String): FollowingFragment {
            val args = Bundle()
            args.putString("username", username)
            val fragment = FollowingFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

