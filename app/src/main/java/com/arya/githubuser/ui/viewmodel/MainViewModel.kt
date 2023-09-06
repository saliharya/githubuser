package com.arya.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.githubuser.api.GitHubResponse
import com.arya.githubuser.api.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val apiKey = "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"
    private val gitHubService = GitHubService.create(apiKey)

    private val _responseLiveData: MutableLiveData<GitHubResponse> = MutableLiveData()
    val responseLiveData: LiveData<GitHubResponse> = _responseLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun fetchGitHubUsers(query: String) {
        _isLoadingLiveData.postValue(true)
        val call = gitHubService.searchUsers("Bearer $apiKey", query)
        call.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                _isLoadingLiveData.postValue(false)
                if (response.isSuccessful) {
                    val gitHubResponse = response.body()
                    _responseLiveData.postValue(gitHubResponse)
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(t)
            }
        })
    }
}