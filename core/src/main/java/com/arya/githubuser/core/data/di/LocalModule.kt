package com.arya.githubuser.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.arya.githubuser.core.data.local.database.FavoriteUserDao
import com.arya.githubuser.core.data.local.database.FavoriteUserRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideFavoriteUserRoomDatabase(
        @ApplicationContext context: Context
    ): FavoriteUserRoomDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteUserRoomDatabase::class.java,
            "favorite_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteUserDao(
        favoriteUserRoomDatabase: FavoriteUserRoomDatabase
    ): FavoriteUserDao {
        return favoriteUserRoomDatabase.favoriteUserDao()
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile("settings")
            }
        )
}