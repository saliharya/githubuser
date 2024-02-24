package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.usecase.GetFavoriteUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavoriteViewModel : ViewModel(), KoinComponent {
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase by inject()

    private val _favoriteUsersLiveData: MutableLiveData<List<GithubUserEntity>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<GithubUserEntity>> = _favoriteUsersLiveData

    fun getAllFavoriteUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteUsersLiveData.postValue(getFavoriteUsersUseCase())
        }
    }
}