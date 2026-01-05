package com.newsaggregator.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.newsaggregator.db.ArticleEntity
import com.newsaggregator.db.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ArticleLocalDataSourceImpl(
    private val database: NewsDatabase,
) : ArticleLocalDataSource {
    private val queries = database.articleQueries

    override fun getAllArticles(): Flow<List<ArticleEntity>> = queries.getAllArticles().asFlow().mapToList(Dispatchers.IO)

    override fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>> =
        queries.getArticlesByCategory(category).asFlow().mapToList(Dispatchers.IO)

    override fun getArticleById(id: String): Flow<ArticleEntity?> = queries.getArticleById(id).asFlow().mapToOneOrNull(Dispatchers.IO)

    override fun searchArticles(query: String): Flow<List<ArticleEntity>> =
        queries.searchArticles(query, query).asFlow().mapToList(Dispatchers.IO)

    override suspend fun insertArticles(articles: List<ArticleEntity>) {
        withContext(Dispatchers.IO) {
            database.transaction {
                articles.forEach { article ->
                    queries.insertArticle(
                        id = article.id,
                        title = article.title,
                        summary = article.summary,
                        content = article.content,
                        author = article.author,
                        imageUrl = article.imageUrl,
                        sourceUrl = article.sourceUrl,
                        sourceName = article.sourceName,
                        category = article.category,
                        publishedAt = article.publishedAt,
                        cachedAt = article.cachedAt,
                    )
                }
            }
        }
    }

    override suspend fun deleteAllArticles() {
        withContext(Dispatchers.IO) {
            queries.deleteAllArticles()
        }
    }

    override suspend fun deleteOldArticles(olderThan: Long) {
        withContext(Dispatchers.IO) {
            queries.deleteOldArticles(olderThan)
        }
    }
}
