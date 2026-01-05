package com.newsaggregator

import androidx.compose.ui.window.ComposeUIViewController
import com.newsaggregator.di.appModules
import com.newsaggregator.di.iosModule
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            if (GlobalContext.getOrNull() == null) {
                startKoin {
                    modules(appModules + iosModule)
                }
            }
        },
    ) {
        App()
    }
