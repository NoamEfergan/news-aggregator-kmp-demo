package com.newsaggregator.data.mapper

import com.newsaggregator.db.ArticleEntity
import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.model.Category
import kotlinx.datetime.Instant

fun ArticleEntity.toDomain(): Article =
    Article(
        id = id,
        title = title,
        summary = summary,
        content = content,
        author = author,
        imageUrl = imageUrl,
        sourceUrl = sourceUrl,
        sourceName = sourceName,
        category = Category.fromString(category),
        publishedAt = Instant.fromEpochMilliseconds(publishedAt),
    )

fun Article.toEntity(cachedAt: Long): ArticleEntity =
    ArticleEntity(
        id = id,
        title = title,
        summary = summary,
        content = content,
        author = author,
        imageUrl = imageUrl,
        sourceUrl = sourceUrl,
        sourceName = sourceName,
        category = category.name,
        publishedAt = publishedAt.toEpochMilliseconds(),
        cachedAt = cachedAt,
    )

fun List<ArticleEntity>.toDomainList(): List<Article> = map { it.toDomain() }
