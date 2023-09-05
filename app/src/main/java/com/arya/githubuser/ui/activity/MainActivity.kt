package com.arya.githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arya.githubuser.api.GitHubService
import com.arya.githubuser.api.GitHubResponse
import com.arya.githubuser.databinding.ActivityMainBinding
import com.arya.githubuser.model.GitHubUser
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<GitHubUser>()
    private lateinit var adapter: ListGitHubUserAdapter
    private val gitHubService = GitHubService.create("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")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGithubUsers.setHasFixedSize(true)

        adapter = ListGitHubUserAdapter(list)
        binding.rvGithubUsers.adapter = adapter
        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    fetchGitHubUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchGitHubUsers(query: String) {
        val call = gitHubService.searchUsers(query)
        call.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                if (response.isSuccessful) {
                    val gitHubResponse = response.body()
                    gitHubResponse?.items?.let {
                        list.clear()
                        list.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                // Handle failure here
            }
        })
    }
}
