package com.arya.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.githubuser.api.GitHubResponse
import com.arya.githubuser.repository.GithubRepository

class MainViewModel : ViewModel() {

    private val githubRepository = GithubRepository()

    private val _responseLiveData: MutableLiveData<GitHubResponse> = MutableLiveData()
    val responseLiveData: LiveData<GitHubResponse> = _responseLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun fetchGitHubUsers(query: String) {
        _isLoadingLiveData.postValue(true)
        githubRepository.fetchGithubUsers(
            query,
            onSuccess = { response ->
                _isLoadingLiveData.postValue(false)
                response?.let {
                    _responseLiveData.postValue(it)
                }
            },
            onFailure = {
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(it)
            }
        )
    }
}