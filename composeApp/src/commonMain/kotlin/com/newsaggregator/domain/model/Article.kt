package com.newsaggregator.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Article(
    val id: String,
    val title: String,
    val summary: String?,
    val content: String?,
    val author: String?,
    val imageUrl: String?,
    val sourceUrl: String,
    val sourceName: String,
    val category: Category,
    val publishedAt: Instant,
) {
    val formattedTime: String
        get() = formatRelativeTime(publishedAt)
}

private fun formatRelativeTime(instant: Instant): String {
    val now = Clock.System.now()
    val duration = now - instant

    return when {
        duration.inWholeMinutes < 1 -> "Just now"
        duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes}m ago"
        duration.inWholeHours < 24 -> "${duration.inWholeHours}h ago"
        duration.inWholeDays < 7 -> "${duration.inWholeDays}d ago"
        else -> "${duration.inWholeDays / 7}w ago"
    }
}
