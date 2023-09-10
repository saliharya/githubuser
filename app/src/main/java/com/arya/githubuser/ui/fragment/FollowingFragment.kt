package com.arya.githubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.arya.githubuser.databinding.FragmentFollowingBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.activity.DetailActivity
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import com.arya.githubuser.ui.viewmodel.FollowingViewModel
import com.arya.githubuser.utils.showToast

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private val followingList = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    private val viewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString("username")

        binding.rvFollowing.setHasFixedSize(true)

        adapter = ListGitHubUserAdapter(followingList) { user ->
            val intent = Intent(activity, DetailActivity::class.java)

            intent.putExtra("user", user)

            startActivity(intent)
        }
        binding.rvFollowing.adapter = adapter

        observeLiveData()

        viewModel.fetchGitHubFollowings(username.orEmpty())
    }

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            adapter?.updateShimmerState(isLoading)
        }
        viewModel.responseLiveData.observe(viewLifecycleOwner) { followings ->
            followings?.let {
                followingList.clear()
                followingList.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { throwable ->
            activity?.showToast(throwable.message)
        }
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

