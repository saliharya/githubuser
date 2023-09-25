package com.arya.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.repository.FavoriteUserRepository
import com.arya.githubuser.model.GithubUser
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private var mFavoriteUserRepository: FavoriteUserRepository? = null

    private val _favoriteUsersLiveData: MutableLiveData<List<GithubUser>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<GithubUser>> = _favoriteUsersLiveData

    fun initRepository(application: Application) {
        mFavoriteUserRepository = FavoriteUserRepository(application)
    }

    fun getAllFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersLiveData.postValue(mFavoriteUserRepository?.getAllFavoriteUsers())
        }
    }
}