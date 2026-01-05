package com.newsaggregator.domain.repository

import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(): Flow<List<Article>>

    fun getArticlesByCategory(category: Category): Flow<List<Article>>

    fun getArticleById(id: String): Flow<Article?>

    fun searchArticles(query: String): Flow<List<Article>>

    suspend fun refreshArticles(): Result<Unit>

    suspend fun refreshCategory(category: Category): Result<Unit>
}
