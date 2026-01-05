package com.newsaggregator.data.mapper

import com.newsaggregator.domain.model.Article
import com.newsaggregator.domain.model.Category
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.absoluteValue

fun RssItem.toArticle(
    category: Category,
    sourceName: String,
): Article {
    val publishedInstant =
        pubDate?.let { parseRssDate(it) }
            ?: Clock.System.now()

    return Article(
        id = generateArticleId(link ?: guid ?: title ?: ""),
        title = title ?: "Untitled",
        summary = description?.stripHtml()?.take(300),
        content = content ?: description,
        author = author,
        imageUrl = image ?: extractImageFromContent(content ?: description),
        sourceUrl = link ?: "",
        sourceName = sourceName,
        category = category,
        publishedAt = publishedInstant,
    )
}

fun RssChannel.toArticles(category: Category): List<Article> {
    val sourceName = title ?: "Unknown Source"
    return items.mapNotNull { item ->
        runCatching { item.toArticle(category, sourceName) }.getOrNull()
    }
}

private fun generateArticleId(input: String): String = input.hashCode().absoluteValue.toString()

private fun parseRssDate(dateString: String): Instant? =
    runCatching {
        // Try ISO format first
        Instant.parse(dateString)
    }.recoverCatching {
        // Try to parse common RSS date formats (RFC 822)
        // Example: "Mon, 01 Jan 2024 12:00:00 GMT"
        parseRfc822Date(dateString)
    }.getOrNull()

private fun parseRfc822Date(dateString: String): Instant {
    // Basic RFC 822 parsing - handles common cases
    val monthMap =
        mapOf(
            "Jan" to 1,
            "Feb" to 2,
            "Mar" to 3,
            "Apr" to 4,
            "May" to 5,
            "Jun" to 6,
            "Jul" to 7,
            "Aug" to 8,
            "Sep" to 9,
            "Oct" to 10,
            "Nov" to 11,
            "Dec" to 12,
        )

    val parts = dateString.replace(",", "").split(" ").filter { it.isNotBlank() }
    if (parts.size < 5) throw IllegalArgumentException("Invalid date format")

    val day = parts[1].toInt()
    val month = monthMap[parts[2]] ?: throw IllegalArgumentException("Invalid month")
    val year = parts[3].toInt()
    val timeParts = parts[4].split(":")
    val hour = timeParts[0].toInt()
    val minute = timeParts.getOrNull(1)?.toInt() ?: 0
    val second = timeParts.getOrNull(2)?.toInt() ?: 0

    val isoString = "${year.pad(4)}-${month.pad(2)}-${day.pad(2)}T${hour.pad(2)}:${minute.pad(2)}:${second.pad(2)}Z"

    return Instant.parse(isoString)
}

private fun Int.pad(length: Int): String = this.toString().padStart(length, '0')

private fun String.stripHtml(): String =
    this
        .replace(Regex("<[^>]*>"), "")
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#039;", "'")
        .replace("&apos;", "'")
        .replace(Regex("\\s+"), " ")
        .trim()

private fun extractImageFromContent(content: String?): String? {
    if (content == null) return null
    val imgRegex = Regex("<img[^>]+src=[\"']([^\"']+)[\"']")
    return imgRegex.find(content)?.groupValues?.getOrNull(1)
}
