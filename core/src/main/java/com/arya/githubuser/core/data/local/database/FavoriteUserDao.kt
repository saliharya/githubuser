package com.arya.githubuser.core.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arya.githubuser.core.data.model.GithubUserDto

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM GithubUserDto")
    suspend fun getFavoriteUsers(): List<GithubUserDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: GithubUserDto)

    @Delete
    suspend fun deleteFavoriteUser(user: GithubUserDto)
}