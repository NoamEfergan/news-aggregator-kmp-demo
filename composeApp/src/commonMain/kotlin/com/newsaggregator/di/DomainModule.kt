package com.newsaggregator.di

import com.newsaggregator.domain.usecase.GetArticleByIdUseCase
import com.newsaggregator.domain.usecase.GetArticlesUseCase
import com.newsaggregator.domain.usecase.RefreshArticlesUseCase
import com.newsaggregator.domain.usecase.SearchArticlesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule =
    module {
        factoryOf(::GetArticlesUseCase)
        factoryOf(::GetArticleByIdUseCase)
        factoryOf(::SearchArticlesUseCase)
        factoryOf(::RefreshArticlesUseCase)
    }
