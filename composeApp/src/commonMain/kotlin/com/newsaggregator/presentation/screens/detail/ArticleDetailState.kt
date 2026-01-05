package com.newsaggregator.presentation.screens.detail

import com.newsaggregator.domain.model.Article

data class ArticleDetailState(
    val article: Article? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)
