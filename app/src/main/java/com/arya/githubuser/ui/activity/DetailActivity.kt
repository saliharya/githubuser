package com.arya.githubuser.ui.activity

import android.app.Application
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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

        (applicationContext as? Application)?.let { viewModel.initRepository(it) }

        viewModel.getFavoriteUsers()
        observeLiveData()

        val user: GithubUser? = intent.getParcelableExtra("user")
        user?.let {
            initializeViews(it)
        }
    }

    private fun initializeViews(user: GithubUser) {
        with(binding) {
            tvUsername.text = user.login
            Glide.with(this@DetailActivity).load(user.avatarUrl).into(ivPicture)
            setUpViewPager(user.login.orEmpty())
            btnFavorite.setOnClickListener {
                viewModel.toggleFavoriteUser()
            }
            btnShare.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this@DetailActivity, R.color.dark_secondary
                )
            )
            btnShare.setOnClickListener {
                shareWithID(user)
            }
        }
    }

    private fun shareWithID(user: GithubUser) {
        val shareText = "GitHub User:${user.htmlUrl}"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
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
                tvName.text = response.name ?: "-"
                tvFollowerCount.text = getString(R.string.follower_count, response.followers ?: 0)
                tvFollowingCount.text = getString(R.string.following_count, response.following ?: 0)
                btnFavorite.isVisible = true

                if (response.isFavorite) {
                    val color = ContextCompat.getColor(
                        this@DetailActivity, R.color.red
                    )
                    btnFavorite.imageTintList = ColorStateList.valueOf(color)
                } else {
                    val color = ContextCompat.getColor(
                        this@DetailActivity, R.color.dark_secondary
                    )
                    btnFavorite.imageTintList = ColorStateList.valueOf(color)
                }
            }
        }
        viewModel.errorLiveData.observe(this) { throwable ->
            showToast(throwable.message)
        }
        viewModel.favoriteUsersLiveData.observe(this) { favoriteUsers ->
            val user: GithubUser? = intent.getParcelableExtra("user")
            val isFavorite = favoriteUsers.any {
                it.id == user?.id
            }
            user?.isFavorite = isFavorite
            user?.let {
                viewModel.setUser(it)
                initializeViews(it)
            }
            viewModel.fetchGitHubUser(user?.login.orEmpty())
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following, R.string.follower
        )
    }
}
