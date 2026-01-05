package com.newsaggregator

import androidx.compose.ui.window.ComposeUIViewController
import com.newsaggregator.di.appModules
import com.newsaggregator.di.iosModule
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            try {
                KoinPlatform.getKoin()
            } catch (e: IllegalStateException) {
                startKoin {
                    modules(appModules + iosModule)
                }
            }
        },
    ) {
        App()
    }
