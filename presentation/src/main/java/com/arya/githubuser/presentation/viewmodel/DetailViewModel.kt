package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.usecase.DeleteFavoriteUserUseCase
import com.arya.githubuser.core.domain.usecase.FetchGithubUserDetailUseCase
import com.arya.githubuser.core.domain.usecase.GetFavoriteUsersUseCase
import com.arya.githubuser.core.domain.usecase.InsertFavoriteUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel : ViewModel(), KoinComponent {

    private val fetchGithubUserDetailUseCase: FetchGithubUserDetailUseCase by inject()
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase by inject()
    private val insertFavoriteUserUseCase: InsertFavoriteUserUseCase by inject()
    private val deleteFavoriteUserUseCase: DeleteFavoriteUserUseCase by inject()

    private val _responseLiveData: MutableLiveData<ResourceState<GithubUserEntity>> =
        MutableLiveData()
    val responseLiveData: LiveData<ResourceState<GithubUserEntity>> = _responseLiveData

    private val _favoriteUsersLiveData: MutableLiveData<List<GithubUserEntity>> = MutableLiveData()
    var favoriteUsersLiveData: LiveData<List<GithubUserEntity>> = _favoriteUsersLiveData

    fun setUser(user: GithubUserEntity) {
        _responseLiveData.postValue(ResourceState.Success(data = user))
    }

    fun fetchGitHubUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchGithubUserDetailUseCase(username).collectLatest { resourceState ->
                if (resourceState is ResourceState.Success) {
                    val isFavorite = favoriteUsersLiveData.value?.any {
                        resourceState.data?.id == it.id
                    } ?: false
                    resourceState.data?.isFavorite = isFavorite
                }
                _responseLiveData.postValue(resourceState)
            }
        }
    }

    fun getFavoriteUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteUsersLiveData.postValue(getFavoriteUsersUseCase())
        }
    }

    fun toggleFavoriteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            responseLiveData.value?.let { resourceState ->
                if (resourceState !is ResourceState.Success) return@let
                val user = resourceState.data ?: return@let
                if (user.isFavorite) deleteFavoriteUserUseCase(user)
                else insertFavoriteUserUseCase(user)

                user.isFavorite = !user.isFavorite

                _responseLiveData.postValue(ResourceState.Success(data = user))
            }
        }

    }
}