package com.newsaggregator.domain.usecase

import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.model.Category
import com.newsaggregator.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticlesUseCase(
    private val repository: ArticleRepository,
) {
    operator fun invoke(category: Category = Category.ALL): Flow<List<Article>> =
        if (category == Category.ALL) {
            repository.getArticles()
        } else {
            repository.getArticlesByCategory(category)
        }
}
