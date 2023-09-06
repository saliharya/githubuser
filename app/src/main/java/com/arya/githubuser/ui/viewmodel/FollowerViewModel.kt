package com.arya.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.githubuser.api.GitHubService
import com.arya.githubuser.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val apiKey = "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"
    private val gitHubService = GitHubService.create(apiKey)

    private val _responseLiveData: MutableLiveData<List<GithubUser>> = MutableLiveData()
    val responseLiveData: LiveData<List<GithubUser>> = _responseLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun fetchGitHubFollowers(username: String) {
        _isLoadingLiveData.postValue(true)
        val call = gitHubService.getFollowers("Bearer $apiKey", username)

        call.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                _isLoadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    val followers = response.body()
                    followers?.let {
                        _responseLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(t)
            }
        })
    }
}