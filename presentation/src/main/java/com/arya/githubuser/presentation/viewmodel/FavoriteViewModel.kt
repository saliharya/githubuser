package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.usecase.GetFavoriteUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase) :
    ViewModel() {
    private val _favoriteUsersLiveData: MutableLiveData<List<GithubUserEntity>?> = MutableLiveData()
    var favoriteUsersLiveData: MutableLiveData<List<GithubUserEntity>?> = _favoriteUsersLiveData

    fun getAllFavoriteUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteUsersLiveData.postValue(getFavoriteUsersUseCase())
        }
    }
}