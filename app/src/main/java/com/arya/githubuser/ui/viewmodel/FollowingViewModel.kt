package com.arya.githubuser.ui.viewmodel

import com.arya.githubuser.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    githubRepository: GithubRepository
) : BaseFollowingFollowerViewModel(githubRepository) {
    override fun fetchData(username: String) {
        _isLoadingLiveData.postValue(true)

        githubRepository.getFollowings(username, onSuccess = { response ->
            _isLoadingLiveData.postValue(false)
            _responseLiveData.postValue(response)
        }, onFailure = {
            _isLoadingLiveData.postValue(false)
            _errorLiveData.postValue(it)
        })
    }

}