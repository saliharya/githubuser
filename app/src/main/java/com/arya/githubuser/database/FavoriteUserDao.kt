package com.arya.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arya.githubuser.model.GithubUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM GithubUser")
    suspend fun getFavoriteUsers(): List<GithubUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: GithubUser)

    @Delete
    suspend fun deleteFavoriteUser(user: GithubUser)
}