package com.arya.githubuser.presentation.di

import com.arya.githubuser.presentation.viewmodel.DetailViewModel
import com.arya.githubuser.presentation.viewmodel.FavoriteViewModel
import com.arya.githubuser.presentation.viewmodel.FollowerViewModel
import com.arya.githubuser.presentation.viewmodel.FollowingViewModel
import com.arya.githubuser.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { FollowerViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { DetailViewModel(get(), get(), get(), get()) }
}