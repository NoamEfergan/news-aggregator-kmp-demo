package com.newsaggregator.domain.usecase

import com.newsaggregator.domain.model.Category
import com.newsaggregator.domain.repository.ArticleRepository

class RefreshArticlesUseCase(
    private val repository: ArticleRepository,
) {
    suspend operator fun invoke(category: Category? = null): Result<Unit> =
        if (category == null || category == Category.ALL) {
            repository.refreshArticles()
        } else {
            repository.refreshCategory(category)
        }
}
