package com.newsaggregator.data.local

import com.newsaggregator.db.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    fun getAllArticles(): Flow<List<ArticleEntity>>

    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    fun getArticleById(id: String): Flow<ArticleEntity?>

    fun searchArticles(query: String): Flow<List<ArticleEntity>>

    suspend fun insertArticles(articles: List<ArticleEntity>)

    suspend fun deleteAllArticles()

    suspend fun deleteOldArticles(olderThan: Long)
}
