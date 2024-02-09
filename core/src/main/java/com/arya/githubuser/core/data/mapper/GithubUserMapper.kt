package com.arya.githubuser.core.data.mapper

import com.arya.githubuser.core.data.model.GithubUserDto
import com.arya.githubuser.core.domain.model.GithubUserEntity

fun GithubUserDto.toEntity() = GithubUserEntity(
    id = id ?: 0L,
    login = login.orEmpty(),
    name = name.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    htmlUrl = htmlUrl.orEmpty(),
    followers = followers ?: 0,
    following = following ?: 0,
    isFavorite = isFavorite,
)

fun GithubUserEntity.toDto() = GithubUserDto(
    id = id,
    login = login,
    name = name,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    followers = followers,
    following = following,
    isFavorite = isFavorite,
)