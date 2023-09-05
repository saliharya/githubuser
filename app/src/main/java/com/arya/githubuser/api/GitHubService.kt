package com.arya.githubuser.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<GitHubResponse>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(apiKey: String): GitHubService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(GitHubService::class.java)
        }
    }
}
