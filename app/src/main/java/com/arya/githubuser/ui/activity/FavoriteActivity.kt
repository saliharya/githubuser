package com.arya.githubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.arya.githubuser.databinding.ActivityFavoriteBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import com.arya.githubuser.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private val favoriteList = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvFavorite.setHasFixedSize(true)

        observeLiveData()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        viewModel.getAllFavoriteUsers()
    }

    private fun observeLiveData() {
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
}