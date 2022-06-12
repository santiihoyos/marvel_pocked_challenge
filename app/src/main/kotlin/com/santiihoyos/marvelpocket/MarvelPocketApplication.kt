package com.santiihoyos.marvelpocket

import android.app.Application
import com.santiihoyos.marvel.di.getApiModule
import com.santiihoyos.marvelpocket.di.appModule
import com.santiihoyos.marvelpocket.di.charactersModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelPocketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MarvelPocketApplication)
            modules(
                appModule,
                getApiModule(
                    apiBaseUrl = BuildConfig.API_BASE_URL,
                    publicKey = BuildConfig.API_PUB_KEY,
                    privateKey = BuildConfig.API_PRIV_KEY,
                ),
                charactersModule
            )
        }
    }
}