package com.arya.githubuser.api

import com.arya.githubuser.model.GithubUser
import com.google.gson.annotations.SerializedName

data class GitHubResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<GithubUser>
)
