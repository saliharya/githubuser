package com.arya.githubuser.presentation.activity

import android.content.Intent
import android.content.res.ColorStateList
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.arya.githubuser.common.base.BaseActivity
import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.common.utils.showToast
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.presentation.R
import com.arya.githubuser.presentation.adapter.SectionsPagerAdapter
import com.arya.githubuser.presentation.databinding.ActivityDetailBinding
import com.arya.githubuser.presentation.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    override val viewModel by viewModel<DetailViewModel>()
    override fun setupViews() {
        viewModel.getFavoriteUsers()
        val user: GithubUserEntity? = intent.getParcelableExtra("user")
        user?.let {
            initializeViews(it)
        }
    }

    override fun observeData() {
        viewModel.responseLiveData.observe(this) { resourceState ->
            when (resourceState) {
                is ResourceState.Loading -> {
                    toggleLoading(true)
                }

                is ResourceState.Success -> {
                    with(binding) {
                        val response = resourceState.data
                        tvName.text = response?.name ?: "-"
                        tvFollowerCount.text =
                            getString(R.string.follower_count, response?.followers ?: 0)
                        tvFollowingCount.text =
                            getString(R.string.following_count, response?.following ?: 0)
                        btnFavorite.isVisible = true

                        if (response?.isFavorite == true) {
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
                        toggleLoading(false)
                    }
                }

                is ResourceState.Error -> {
                    toggleLoading(false)
                    showToast(resourceState.message)
                }
            }
        }

        viewModel.favoriteUsersLiveData.observe(this) { favoriteUsers ->
            val user: GithubUserEntity? = intent.getParcelableExtra("user")
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

    private fun initializeViews(user: GithubUserEntity) {
        with(binding) {
            tvUsername.text = user.login
            Glide.with(this@DetailActivity).load(user.avatarUrl).into(ivPicture)
            setUpViewPager(user.login)
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

    private fun shareWithID(user: GithubUserEntity) {
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

    private fun toggleLoading(isLoading: Boolean) {
        with(binding) {
            shimmerLayout.isVisible = isLoading
            dataLayout.isVisible = !isLoading
            if (isLoading) shimmerLayout.startShimmer()
            else shimmerLayout.stopShimmer()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following, R.string.follower
        )
    }
}
