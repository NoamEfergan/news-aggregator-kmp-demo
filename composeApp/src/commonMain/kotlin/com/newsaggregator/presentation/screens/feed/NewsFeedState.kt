package com.newsaggregator.presentation.screens.feed

import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.model.Category

data class NewsFeedState(
    val articles: List<Article> = emptyList(),
    val selectedCategory: Category = Category.ALL,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
)
