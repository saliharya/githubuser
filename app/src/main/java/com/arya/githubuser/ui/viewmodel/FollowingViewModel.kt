package com.arya.githubuser.ui.viewmodel

class FollowingViewModel : BaseFollowingFollowerViewModel() {
    override fun fetchData(username: String) {
        _isLoadingLiveData.postValue(true)

        githubRepository.getFollowings(
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