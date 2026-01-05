package com.newsaggregator.data.repository

import com.newsaggregator.data.local.ArticleLocalDataSource
import com.newsaggregator.data.mapper.toArticles
import com.newsaggregator.data.mapper.toDomain
import com.newsaggregator.data.mapper.toDomainList
import com.newsaggregator.data.mapper.toEntity
import com.newsaggregator.data.remote.RssService
import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.model.Category
import com.newsaggregator.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class ArticleRepositoryImpl(
    private val rssService: RssService,
    private val localDataSource: ArticleLocalDataSource,
) : ArticleRepository {
    override fun getArticles(): Flow<List<Article>> = localDataSource.getAllArticles().map { it.toDomainList() }

    override fun getArticlesByCategory(category: Category): Flow<List<Article>> =
        if (category == Category.ALL) {
            getArticles()
        } else {
            localDataSource.getArticlesByCategory(category.name).map { it.toDomainList() }
        }

    override fun getArticleById(id: String): Flow<Article?> = localDataSource.getArticleById(id).map { it?.toDomain() }

    override fun searchArticles(query: String): Flow<List<Article>> = localDataSource.searchArticles(query).map { it.toDomainList() }

    override suspend fun refreshArticles(): Result<Unit> =
        runCatching {
            val results = rssService.fetchAllFeeds()
            val articles =
                results.flatMap { (category, result) ->
                    result.getOrNull()?.toArticles(category) ?: emptyList()
                }

            val currentTime = Clock.System.now().toEpochMilliseconds()
            val entities = articles.map { it.toEntity(currentTime) }

            localDataSource.insertArticles(entities)

            // Clean up articles older than 7 days
            val oneWeekAgo = currentTime - (7 * 24 * 60 * 60 * 1000L)
            localDataSource.deleteOldArticles(oneWeekAgo)
        }

    override suspend fun refreshCategory(category: Category): Result<Unit> =
        runCatching {
            val results = rssService.fetchFeedsByCategory(category)
            val articles =
                results.flatMap { result ->
                    result.getOrNull()?.toArticles(category) ?: emptyList()
                }

            val currentTime = Clock.System.now().toEpochMilliseconds()
            val entities = articles.map { it.toEntity(currentTime) }

            localDataSource.insertArticles(entities)
        }
}
