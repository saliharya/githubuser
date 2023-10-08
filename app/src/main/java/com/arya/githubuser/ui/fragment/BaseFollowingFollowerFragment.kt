package com.arya.githubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arya.githubuser.databinding.FragmentFollowerBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.activity.DetailActivity
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import com.arya.githubuser.ui.viewmodel.BaseFollowingFollowerViewModel
import com.arya.githubuser.utils.showToast

abstract class BaseFollowingFollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private val followerList = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    abstract val viewModel: BaseFollowingFollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString("username")

        binding.rvFollower.setHasFixedSize(true)

        adapter = ListGitHubUserAdapter(followerList) { user ->
            val intent = Intent(activity, DetailActivity::class.java)

            intent.putExtra("user", user)

            startActivity(intent)
        }
        binding.rvFollower.adapter = adapter

        observeLiveData()

        fetchData(username.orEmpty())
    }

    abstract fun fetchData(username: String)

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            with(binding) {
                rvFollower.isVisible = !isLoading
                sfl.run {
                    isVisible = isLoading
                    if (isLoading) startShimmer() else stopShimmer()
                }
            }
        }
        viewModel.responseLiveData.observe(viewLifecycleOwner) { followers ->
            followers?.let {
                followerList.clear()
                followerList.addAll(it)
                adapter?.updateData(followerList)

                if (followerList.isEmpty()) {
                    binding.tvNoData.visibility = View.VISIBLE
                } else {
                    binding.tvNoData.visibility = View.GONE
                }
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { throwable ->
            activity?.showToast(throwable.message)
        }
    }
}

