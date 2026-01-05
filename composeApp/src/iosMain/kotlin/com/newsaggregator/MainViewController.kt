package com.newsaggregator

import androidx.compose.ui.window.ComposeUIViewController
import com.newsaggregator.di.appModules
import com.newsaggregator.di.iosModule
import org.koin.core.context.startKoin

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            startKoin {
                modules(appModules + iosModule)
            }
        },
    ) {
        App()
    }
