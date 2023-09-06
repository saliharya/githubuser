package com.arya.githubuser.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.arya.githubuser.R
import com.arya.githubuser.api.GitHubService
import com.arya.githubuser.databinding.ActivityDetailBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val apiKey = "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"
    private val gitHubService = GitHubService.create(apiKey)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Retrieve data from the Intent
        val user: GithubUser? = intent.getParcelableExtra("user")
        fetchGitHubUsers(user?.login.orEmpty())


        // Update the UI elements
        with(binding) {
            tvUsername.text = user?.login
            Glide.with(this@DetailActivity).load(user?.avatar_url).into(ivPicture)
        }
    }

    private fun fetchGitHubUsers(username: String) {
        val call = gitHubService.getUserDetail("Bearer $apiKey", username)
        call.enqueue(object : Callback<GithubUser> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GithubUser>,
                response: Response<GithubUser>
            ) {
                if (response.isSuccessful) {
                    val gitHubResponse = response.body()
                    with(binding) {
                        tvFollowerCount.text =
                            getString(R.string.follower_count, gitHubResponse?.followers ?: 0)
                        tvFollowingCount.text =
                            getString(R.string.following_count, gitHubResponse?.following ?: 0)
                        setUpViewPager(username)
                    }
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(
                        this@DetailActivity,
                        t.message ?: "Fail to fetch data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun ActivityDetailBinding.setUpViewPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, username)
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.follower
        )
    }
}

