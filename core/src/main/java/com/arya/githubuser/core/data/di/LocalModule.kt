package com.arya.githubuser.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.arya.githubuser.core.data.local.database.FavoriteUserRoomDatabase
import com.arya.githubuser.core.domain.usecase.DeleteFavoriteUserUseCase
import com.arya.githubuser.core.domain.usecase.InsertFavoriteUserUseCase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { InsertFavoriteUserUseCase(get()) }
    single { DeleteFavoriteUserUseCase(get()) }

    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("salih".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FavoriteUserRoomDatabase::class.java,
            "favorite_database"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
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
