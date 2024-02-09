package com.arya.githubuser.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserEntity(
    val id: Long,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val followers: Int,
    val following: Int,
    var isFavorite: Boolean,
): Parcelable