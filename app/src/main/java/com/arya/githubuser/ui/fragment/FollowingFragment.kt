package com.arya.githubuser.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arya.githubuser.api.GitHubService
import com.arya.githubuser.databinding.FragmentFollowingBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.activity.DetailActivity
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import com.arya.githubuser.utils.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private val followingList = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    private val apiKey = "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" // Replace with your API key
    private val gitHubService = GitHubService.create(apiKey)

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

        // Call your method to fetch and display Followings here
        fetchGitHubFollowings(username.orEmpty())
    }

    private fun fetchGitHubFollowings(username: String) {
        // Make a request to fetch GitHub Followings using your API interface
        val call = gitHubService.getFollowing("Bearer $apiKey", username)

        call.enqueue(object : Callback<List<GithubUser>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                if (response.isSuccessful) {
                    val followings = response.body()
                    followings?.let {
                        followingList.clear()
                        followingList.addAll(it)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                activity?.runOnUiThread {
                    activity?.showToast(t.message)
                }
            }
        })
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

