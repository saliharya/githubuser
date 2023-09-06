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
import com.arya.githubuser.databinding.FragmentFollowerBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.activity.DetailActivity
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private val followerList = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    private val apiKey = "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" // Replace with your API key
    private val gitHubService = GitHubService.create(apiKey)

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

        // Call your method to fetch and display followers here
        fetchGitHubFollowers(username.orEmpty())
    }

    private fun fetchGitHubFollowers(username: String) {
        // Make a request to fetch GitHub followers using your API interface
        val call = gitHubService.getFollowers("Bearer $apiKey", username)

        call.enqueue(object : Callback<List<GithubUser>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                if (response.isSuccessful) {
                    val followers = response.body()
                    followers?.let {
                        followerList.clear()
                        followerList.addAll(it)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        activity,
                        t.message ?: "Fail to fetch data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
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

