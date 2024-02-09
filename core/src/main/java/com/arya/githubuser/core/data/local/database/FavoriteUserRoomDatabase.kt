package com.arya.githubuser.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arya.githubuser.core.data.model.GithubUserDto

@Database(entities = [GithubUserDto::class], version = 1)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}