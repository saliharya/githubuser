package com.arya.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.model.GithubUser
import com.arya.githubuser.repository.FavoriteUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private var mFavoriteUserRepository: FavoriteUserRepository
) : ViewModel() {

    private val _favoriteUsersLiveData: MutableLiveData<List<GithubUser>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<GithubUser>> = _favoriteUsersLiveData

    fun getAllFavoriteUsers() {
        viewModelScope.launch {
            _favoriteUsersLiveData.postValue(mFavoriteUserRepository.getFavoriteUsers())
        }
    }
}