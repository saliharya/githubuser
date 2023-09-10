package com.arya.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.arya.githubuser.ui.viewmodel.FollowingViewModel

class FollowingFragment : BaseFollowingFollowerFragment() {
    override val viewModel by viewModels<FollowingViewModel>()

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

