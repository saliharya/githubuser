package com.arya.githubuser.ui.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.arya.githubuser.databinding.ActivityFavoriteBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import com.arya.githubuser.ui.viewmodel.FavoriteViewModel


class FavoriteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private val favoriteList = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        (applicationContext as? Application)?.let { viewModel.initRepository(it) }

        binding.rvFavorite.setHasFixedSize(true)

        fetchData()
    }

    private fun fetchData() {
        viewModel.getAllFavoriteUsers()?.observe(this) {
            it?.let {
                favoriteList.clear()
                favoriteList.addAll(it)
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