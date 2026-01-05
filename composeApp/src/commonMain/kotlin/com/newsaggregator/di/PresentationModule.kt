package com.newsaggregator.di

import com.newsaggregator.presentation.screens.detail.ArticleDetailViewModel
import com.newsaggregator.presentation.screens.feed.NewsFeedViewModel
import com.newsaggregator.presentation.screens.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule =
    module {
        viewModelOf(::NewsFeedViewModel)
        viewModelOf(::SearchViewModel)
        viewModel { params -> ArticleDetailViewModel(params.get(), get()) }
    }
