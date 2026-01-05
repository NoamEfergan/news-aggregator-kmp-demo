package com.newsaggregator

import android.app.Application
import com.newsaggregator.di.androidModule
import com.newsaggregator.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModules + androidModule)
        }
    }
}
