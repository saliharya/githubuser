package com.arya.githubuser.di

import com.arya.githubuser.BuildConfig
import com.arya.githubuser.api.AuthInterceptor
import com.arya.githubuser.api.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(AuthInterceptor(BuildConfig.API_KEY)).build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideGithubService(retrofit: Retrofit): GitHubService =
        retrofit.create(GitHubService::class.java)
}