package com.arya.githubuser

import androidx.multidex.MultiDexApplication
import com.arya.githubuser.core.data.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubUserApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GithubUserApp)
            modules(listOf(remoteModule, localModule, preferencesModule, repositoryModule))
        }
    }
}
