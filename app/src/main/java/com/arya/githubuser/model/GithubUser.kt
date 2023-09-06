package com.arya.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val login: String?,
    val name: String?,
    val id: Long?,
    val avatar_url: String?,
    val html_url: String?,
    val followers: Int?,
    val following: Int?
    // Add other properties as needed
) : Parcelable

