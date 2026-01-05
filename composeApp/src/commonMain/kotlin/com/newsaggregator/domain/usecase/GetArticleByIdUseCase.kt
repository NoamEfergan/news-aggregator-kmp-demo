package com.newsaggregator.domain.usecase

import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleByIdUseCase(
    private val repository: ArticleRepository,
) {
    operator fun invoke(articleId: String): Flow<Article?> = repository.getArticleById(articleId)
}
