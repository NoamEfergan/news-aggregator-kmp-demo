package com.newsaggregator.di

import com.newsaggregator.data.local.DatabaseDriverFactory
import org.koin.dsl.module

val iosModule =
    module {
        single { DatabaseDriverFactory().createDriver() }
    }
