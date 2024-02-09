package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.usecase.GetFavoriteUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private var getFavoriteUsersUseCase: GetFavoriteUsersUseCase
) : ViewModel() {

    private val _favoriteUsersLiveData: MutableLiveData<List<GithubUserEntity>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<GithubUserEntity>> = _favoriteUsersLiveData

    fun getAllFavoriteUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteUsersLiveData.postValue(getFavoriteUsersUseCase())
        }
    }
}