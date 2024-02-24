package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arya.githubuser.common.utils.ResourceState
import com.arya.githubuser.core.domain.model.GithubUserEntity
import com.arya.githubuser.core.domain.usecase.FetchGithubUserUseCase
import com.arya.githubuser.core.domain.usecase.GetThemeSettingUseCase
import com.arya.githubuser.core.domain.usecase.SaveThemeSettingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val getThemeSettingUseCase: GetThemeSettingUseCase by inject()
    private val saveThemeSettingUseCase: SaveThemeSettingUseCase by inject()
    private val fetchGithubUserUseCase: FetchGithubUserUseCase by inject()

    private val _responseLiveData = MutableLiveData<ResourceState<List<GithubUserEntity>>>()
    val responseLiveData: LiveData<ResourceState<List<GithubUserEntity>>> = _responseLiveData

    fun getThemeSettings(): LiveData<Boolean> {
        return getThemeSettingUseCase().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            saveThemeSettingUseCase(isDarkModeActive)
        }
    }

    fun fetchGitHubUsers(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchGithubUserUseCase(query).collectLatest(_responseLiveData::postValue)
        }
    }
}