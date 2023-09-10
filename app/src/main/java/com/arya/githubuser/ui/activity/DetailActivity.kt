package com.arya.githubuser.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.arya.githubuser.R
import com.arya.githubuser.databinding.ActivityDetailBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.adapter.SectionsPagerAdapter
import com.arya.githubuser.ui.viewmodel.DetailViewModel
import com.arya.githubuser.utils.showToast
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val user: GithubUser? = intent.getParcelableExtra("user")
        viewModel.fetchGitHubUsers(user?.login.orEmpty())

        user?.let { initializeViews(it) }
        observeLiveData()
    }

    private fun initializeViews(user: GithubUser) {
        with(binding) {
            tvUsername.text = user.login
            Glide.with(this@DetailActivity).load(user.avatar_url).into(ivPicture)
            setUpViewPager(user.login.orEmpty())
        }
    }

    private fun ActivityDetailBinding.setUpViewPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, username)
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(this) { isLoading ->
            with(binding) {
                shimmerLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading) shimmerLayout.startShimmer() else shimmerLayout.stopShimmer()
                dataLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        }
        viewModel.responseLiveData.observe(this) { response ->
            with(binding) {
                tvName.text = getString(R.string.name, response.name)
                tvFollowerCount.text = getString(R.string.follower_count, response.followers ?: 0)
                tvFollowingCount.text = getString(R.string.following_count, response.following ?: 0)
            }
        }
        viewModel.errorLiveData.observe(this) { throwable ->
            showToast(throwable.message)
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following, R.string.follower
        )
    }
}
