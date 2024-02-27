package com.arya.githubuser.favorite.activity

import android.content.Intent
import android.view.View
import com.arya.githubuser.common.base.BaseActivity
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.favorite.databinding.ActivityFavoriteBinding
import com.arya.githubuser.favorite.di.favoriteModule
import com.arya.githubuser.favorite.viewmodel.FavoriteViewModel
import com.arya.githubuser.presentation.activity.DetailActivity
import com.arya.githubuser.presentation.adapter.ListGitHubUserAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding, FavoriteViewModel>() {

    private val favoriteList = ArrayList<GithubUserEntity>()
    private var adapter: ListGitHubUserAdapter? = null

    override val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    override val viewModel by viewModel<FavoriteViewModel>()

    override fun setupViews() {
        loadKoinModules(favoriteModule)
        binding.rvFavorite.setHasFixedSize(true)
    }

    override fun observeData() {
        viewModel.favoriteUsersLiveData.observe(this) {

            val noDataTextView = binding.tvNoData

            it?.let {
                favoriteList.clear()
                favoriteList.addAll(it)

                if (favoriteList.isEmpty()) {
                    noDataTextView.visibility = View.VISIBLE
                    binding.rvFavorite.visibility = View.GONE
                } else {
                    noDataTextView.visibility = View.GONE
                    binding.rvFavorite.visibility = View.VISIBLE
                }

                adapter = ListGitHubUserAdapter(favoriteList) { user ->
                    val intent = Intent(this, DetailActivity::class.java)

                    intent.putExtra("user", user)

                    startActivity(intent)
                }
                binding.rvFavorite.adapter = adapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        viewModel.getAllFavoriteUsers()
    }
}