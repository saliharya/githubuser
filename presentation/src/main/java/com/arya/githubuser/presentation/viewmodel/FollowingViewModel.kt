package com.arya.githubuser.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.arya.githubuser.core.domain.usecase.GetFollowingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowingViewModel : BaseFollowingFollowerViewModel(), KoinComponent {
    private val getFollowingUseCase: GetFollowingUseCase by inject()

    override fun fetchData(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFollowingUseCase(username).collectLatest(_responseLiveData::postValue)
        }
    }
}