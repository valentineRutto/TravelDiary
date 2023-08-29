package com.valentinerutto.traveldiary

import android.app.Application
import com.valentinerutto.traveldiary.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App: Application() {
    companion object {
        lateinit var INSTANCE: App
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(appModule)
        }
    }
}