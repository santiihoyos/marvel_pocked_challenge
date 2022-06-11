package com.santiihoyos.marvelpocket

import android.app.Application
import com.santiihoyos.marvel.di.apiModule
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
                apiModule,
                charactersModule
            )
        }
    }
}