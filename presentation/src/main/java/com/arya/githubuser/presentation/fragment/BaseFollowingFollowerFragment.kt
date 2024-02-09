package com.arya.githubuser.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arya.githubuser.common.base.BaseFragment
import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.common.utils.showToast
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.presentation.activity.DetailActivity
import com.arya.githubuser.presentation.adapter.ListGitHubUserAdapter
import com.arya.githubuser.presentation.databinding.FragmentFollowerBinding
import com.arya.githubuser.presentation.viewmodel.BaseFollowingFollowerViewModel

abstract class BaseFollowingFollowerFragment : BaseFragment<FragmentFollowerBinding, BaseFollowingFollowerViewModel>() {

    private val followerList = ArrayList<GithubUserEntity>()
    private var adapter: ListGitHubUserAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFollowerBinding.inflate(inflater, container, false)

    override fun setupViews() {
        val username = arguments?.getString("username")

        binding.rvFollower.setHasFixedSize(true)

        adapter = ListGitHubUserAdapter(followerList) { user ->
            val intent = Intent(activity, DetailActivity::class.java)

            intent.putExtra("user", user)

            startActivity(intent)
        }
        binding.rvFollower.adapter = adapter

        fetchData(username.orEmpty())
    }

    override fun observeData() {
        viewModel.responseLiveData.observe(viewLifecycleOwner) { resourceState ->
            when (resourceState) {
                is ResourceState.Loading -> toggleLoading(true)

                is ResourceState.Success -> {
                    resourceState.data?.let {
                        followerList.clear()
                        followerList.addAll(it)
                        adapter?.updateData(followerList)

                        if (followerList.isEmpty()) {
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.tvNoData.visibility = View.GONE
                        }
                    }
                    toggleLoading(false)
                }

                is ResourceState.Error -> {
                    toggleLoading(false)
                    activity?.showToast(resourceState.message)
                }
            }
        }
    }

    abstract fun fetchData(username: String)

    private fun toggleLoading(isLoading: Boolean) {
        with(binding) {
            rvFollower.isVisible = !isLoading
            sfl.run {
                isVisible = isLoading
                if (isLoading) startShimmer() else stopShimmer()
            }
        }
    }
}

