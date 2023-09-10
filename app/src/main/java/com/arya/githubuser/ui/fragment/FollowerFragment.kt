package com.arya.githubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.arya.githubuser.databinding.FragmentFollowerBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.activity.DetailActivity
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import com.arya.githubuser.ui.viewmodel.FollowerViewModel
import com.arya.githubuser.utils.showToast

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private val followerList = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    private val viewModel by viewModels<FollowerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        viewModel.fetchGitHubFollowers(username.orEmpty())
    }

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            adapter?.updateShimmerState(isLoading)
        }
        viewModel.responseLiveData.observe(viewLifecycleOwner) { followers ->
            followers?.let {
                followerList.clear()
                followerList.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { throwable ->
            activity?.showToast(throwable.message)
        }
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

