package com.arya.githubuser.presentation.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.arya.githubuser.common.base.BaseActivity
import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.common.utils.showToast
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.presentation.R
import com.arya.githubuser.presentation.adapter.ListGitHubUserAdapter
import com.arya.githubuser.presentation.databinding.ActivityMainBinding
import com.arya.githubuser.presentation.viewmodel.MainViewModel
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val list = ArrayList<GithubUserEntity>()
    private var adapter: ListGitHubUserAdapter? = null

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override val viewModel by viewModels<MainViewModel>()

    override fun setupViews() {
        val switchTheme = findViewById<SwitchMaterial>(R.id.switchTheme)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }

        with(binding) {
            rvGithubUsers.setHasFixedSize(true)

            adapter = ListGitHubUserAdapter(list) { user ->
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
            rvGithubUsers.adapter = adapter

            btnFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModel.fetchGitHubUsers(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?) = false
            })
        }
    }

    override fun observeData() {
        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        viewModel.responseLiveData.observe(this) { resourceState ->
            when (resourceState) {
                is ResourceState.Loading -> toggleLoading(true)
                is ResourceState.Success -> {
                    val response = resourceState.data.orEmpty()

                    list.clear()
                    list.addAll(response)
                    adapter?.updateData(response)

                    binding.tvNoData.isVisible = response.isEmpty()
                    binding.rvGithubUsers.isVisible = response.isNotEmpty()

                    toggleLoading(false)
                }

                is ResourceState.Error -> {
                    toggleLoading(false)
                    showToast(resourceState.message)
                }
            }
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        with(binding) {
            rvGithubUsers.isVisible = !isLoading
            sfl.run {
                isVisible = isLoading
                if (isLoading) startShimmer() else stopShimmer()
            }
            if (isLoading) {
                tvNoData.isVisible = false
            }
        }
    }
}
