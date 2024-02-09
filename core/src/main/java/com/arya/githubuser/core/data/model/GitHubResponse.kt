package com.arya.githubuser.core.data.model

import com.google.gson.annotations.SerializedName

data class GitHubResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<GithubUserDto>
)
