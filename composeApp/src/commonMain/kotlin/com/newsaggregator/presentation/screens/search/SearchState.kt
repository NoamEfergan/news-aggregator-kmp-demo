package com.newsaggregator.presentation.screens.search

import com.newsaggregator.domain.model.Article

data class SearchState(
    val query: String = "",
    val results: List<Article> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null,
)
