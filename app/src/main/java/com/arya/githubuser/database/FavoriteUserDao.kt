package com.arya.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arya.githubuser.model.GithubUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(githubUser: GithubUser)

    @Query("DELETE from githubuser WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * from githubuser ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<GithubUser>>
}