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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchGithubUserDetailUseCase: FetchGithubUserDetailUseCase,
    private var getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
    private var insertFavoriteUserUseCase: InsertFavoriteUserUseCase,
    private var deleteFavoriteUserUseCase: DeleteFavoriteUserUseCase,
) : ViewModel() {

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