package com.arya.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.repository.GithubRepository

abstract class BaseFollowingFollowerViewModel : ViewModel() {
    protected val githubRepository = GithubRepository()

    protected val _responseLiveData: MutableLiveData<List<GithubUser>> = MutableLiveData()
    val responseLiveData: LiveData<List<GithubUser>> = _responseLiveData

    protected val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    protected val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    abstract fun fetchData(username: String)
}