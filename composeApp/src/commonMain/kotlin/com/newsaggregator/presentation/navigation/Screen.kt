package com.newsaggregator.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Feed : Screen

    @Serializable
    data class ArticleDetail(
        val articleId: String,
    ) : Screen

    @Serializable
    data object Search : Screen
}
