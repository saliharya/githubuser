package com.arya.githubuser.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.arya.githubuser.core.data.local.database.FavoriteUserRoomDatabase
import com.arya.githubuser.core.domain.usecase.DeleteFavoriteUserUseCase
import com.arya.githubuser.core.domain.usecase.FetchGithubUserUseCase
import com.arya.githubuser.core.domain.usecase.InsertFavoriteUserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { InsertFavoriteUserUseCase() }
    single { DeleteFavoriteUserUseCase() }

    single {
        Room.databaseBuilder(
            androidContext(),
            FavoriteUserRoomDatabase::class.java,
            "favorite_database"
        ).build()
    }

    single {
        get<FavoriteUserRoomDatabase>().favoriteUserDao()
    }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidContext().preferencesDataStoreFile("settings")
            }
        )
    }
}
