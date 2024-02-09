package com.arya.githubuser.core.data.di

import com.arya.githubuser.core.BuildConfig
import com.arya.githubuser.core.data.remote.AuthInterceptor
import com.arya.githubuser.core.data.remote.GitHubService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(AuthInterceptor(BuildConfig.API_KEY)).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideGithubService(
        retrofit: Retrofit,
        gson: Gson
    ): GitHubService =
        retrofit.create(GitHubService::class.java)
}