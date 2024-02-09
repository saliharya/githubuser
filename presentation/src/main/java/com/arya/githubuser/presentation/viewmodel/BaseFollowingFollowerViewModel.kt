package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.core.domain.model.GithubUserEntity

abstract class BaseFollowingFollowerViewModel : ViewModel() {

    protected val _responseLiveData = MutableLiveData<ResourceState<List<GithubUserEntity>>>()
    val responseLiveData: LiveData<ResourceState<List<GithubUserEntity>>> = _responseLiveData

    abstract fun fetchData(username: String)
}