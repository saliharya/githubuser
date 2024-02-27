package com.arya.githubuser.core.data.di

import com.arya.githubuser.core.BuildConfig
import com.arya.githubuser.core.data.remote.AuthInterceptor
import com.arya.githubuser.core.data.remote.GitHubService
import com.arya.githubuser.core.domain.usecase.FetchGithubUserDetailUseCase
import com.arya.githubuser.core.domain.usecase.FetchGithubUserUseCase
import com.arya.githubuser.core.domain.usecase.GetFavoriteUsersUseCase
import com.arya.githubuser.core.domain.usecase.GetFollowerUseCase
import com.arya.githubuser.core.domain.usecase.GetFollowingUseCase
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    single { Gson() }

    single { FetchGithubUserUseCase(get()) }
    single { FetchGithubUserDetailUseCase(get()) }
    single { GetFavoriteUsersUseCase(get()) }
    single { GetFollowerUseCase(get()) }
    single { GetFollowingUseCase(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create(GitHubService::class.java)
    }
}
