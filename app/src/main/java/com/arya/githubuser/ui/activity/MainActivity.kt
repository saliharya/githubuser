package com.arya.githubuser.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.arya.githubuser.api.GitHubResponse
import com.arya.githubuser.api.GitHubService
import com.arya.githubuser.databinding.ActivityMainBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val list = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    private val apiKey = "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"
    private val gitHubService = GitHubService.create(apiKey)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            rvGithubUsers.setHasFixedSize(true)

            adapter = ListGitHubUserAdapter(list) { user ->
                val intent = Intent(this@MainActivity, DetailActivity::class.java)

                intent.putExtra("user", user)

                startActivity(intent)
            }
            rvGithubUsers.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        fetchGitHubUsers(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?) = false
            })
        }
    }

    private fun fetchGitHubUsers(query: String) {
        val call = gitHubService.searchUsers("Bearer $apiKey", query)
        call.enqueue(object : Callback<GitHubResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                if (response.isSuccessful) {
                    val gitHubResponse = response.body()
                    gitHubResponse?.items?.let {
                        list.clear()
                        list.addAll(it)
                        adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        t.message ?: "Fail to fetch data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
