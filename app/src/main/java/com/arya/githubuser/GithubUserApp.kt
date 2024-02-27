package com.arya.githubuser

import androidx.multidex.MultiDex
import com.arya.githubuser.core.data.di.localModule
import com.arya.githubuser.core.data.di.preferencesModule
import com.arya.githubuser.core.data.di.remoteModule
import com.arya.githubuser.core.data.di.repositoryModule
import com.arya.githubuser.presentation.di.presentationModule
import com.google.android.play.core.splitcompat.SplitCompatApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubUserApp : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            androidContext(this@GithubUserApp)
            modules(
                listOf(
                    remoteModule,
                    localModule,
                    preferencesModule,
                    repositoryModule,
                    presentationModule
                )
            )
        }
    }
}
