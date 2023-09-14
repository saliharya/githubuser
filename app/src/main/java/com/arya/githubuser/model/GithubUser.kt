package com.arya.githubuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GithubUser(
    @PrimaryKey
    val id: Long?,
    val login: String?,
    val name: String?,
    val avatar_url: String?,
    val html_url: String?,
    val followers: Int?,
    val following: Int?,
    var isFavorite: Boolean = false
) : Parcelable

