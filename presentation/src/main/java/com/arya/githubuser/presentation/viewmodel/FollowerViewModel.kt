package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.arya.githubuser.core.domain.usecase.GetFollowerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowerViewModel : BaseFollowingFollowerViewModel(), KoinComponent {
    private val getFollowerUseCase: GetFollowerUseCase by inject()

    override fun fetchData(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFollowerUseCase(username).collectLatest(_responseLiveData::postValue)
        }
    }
}