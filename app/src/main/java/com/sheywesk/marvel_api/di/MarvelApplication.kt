package com.sheywesk.marvel_api.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MarvelApplication)
            modules(
                listOf(
                    databaseModules,
                    presentationModule,
                    dataModules
                )
            )
        }
    }
}