package com.arya.githubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.arya.githubuser.database.FavoriteUserDao
import com.arya.githubuser.database.FavoriteUserRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
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
    fun provideFavoriteUserDao(
        favoriteUserRoomDatabase: FavoriteUserRoomDatabase
    ): FavoriteUserDao {
        return favoriteUserRoomDatabase.favoriteUserDao()
    }

    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile("settings")
            }
        )
}