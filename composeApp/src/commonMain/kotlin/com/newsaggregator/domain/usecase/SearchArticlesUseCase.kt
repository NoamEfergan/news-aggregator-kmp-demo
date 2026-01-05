package com.newsaggregator.domain.usecase

import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchArticlesUseCase(
    private val repository: ArticleRepository,
) {
    operator fun invoke(query: String): Flow<List<Article>> {
        if (query.isBlank()) return flowOf(emptyList())
        return repository.searchArticles(query.trim())
    }
}
