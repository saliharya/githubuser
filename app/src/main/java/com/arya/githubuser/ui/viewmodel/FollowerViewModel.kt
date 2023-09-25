package com.arya.githubuser.ui.viewmodel

class FollowerViewModel : BaseFollowingFollowerViewModel() {
    override fun fetchData(username: String) {
        _isLoadingLiveData.postValue(true)

        githubRepository.getFollowers(
            username,
            onSuccess = { response ->
                _isLoadingLiveData.postValue(false)
                _responseLiveData.postValue(response)
            },
            onFailure = {
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(it)
            }
        )
    }

}