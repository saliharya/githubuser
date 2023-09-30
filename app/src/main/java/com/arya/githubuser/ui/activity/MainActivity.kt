package com.arya.githubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.arya.githubuser.databinding.ActivityMainBinding
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.ui.adapter.ListGitHubUserAdapter
import com.arya.githubuser.ui.viewmodel.MainViewModel
import com.arya.githubuser.utils.showToast

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()
    private val list = ArrayList<GithubUser>()
    private var adapter: ListGitHubUserAdapter? = null

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

            observeLiveData()
        }
    }

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(this) { isLoading ->
            adapter?.updateShimmerState(isLoading)
        }
        viewModel.responseLiveData.observe(this) { response ->
            response.items.let {
                list.clear()
                list.addAll(it)
                adapter?.notifyDataSetChanged()
            }

            val noDataTextView = binding.tvNoData
            val recyclerView = binding.rvGithubUsers

            if (response.totalCount == 0) {
                noDataTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                noDataTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
        viewModel.errorLiveData.observe(this) { throwable ->
            showToast(throwable.message)
        }
    }
}
