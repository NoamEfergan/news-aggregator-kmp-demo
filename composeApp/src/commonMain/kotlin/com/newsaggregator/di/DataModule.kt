package com.newsaggregator.di

import com.newsaggregator.data.local.ArticleLocalDataSource
import com.newsaggregator.data.local.ArticleLocalDataSourceImpl
import com.newsaggregator.data.remote.RssService
import com.newsaggregator.data.remote.RssServiceImpl
import com.newsaggregator.data.repository.ArticleRepositoryImpl
import com.newsaggregator.db.NewsDatabase
import com.newsaggregator.domain.repository.ArticleRepository
import com.prof18.rssparser.RssParser
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule =
    module {
        single { NewsDatabase(get()) }
        single { RssParser() }

        singleOf(::ArticleLocalDataSourceImpl) bind ArticleLocalDataSource::class
        singleOf(::RssServiceImpl) bind RssService::class
        singleOf(::ArticleRepositoryImpl) bind ArticleRepository::class
    }
