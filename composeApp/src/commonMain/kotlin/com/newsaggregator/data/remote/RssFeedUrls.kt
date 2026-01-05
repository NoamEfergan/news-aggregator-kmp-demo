package com.newsaggregator.data.remote

import com.newsaggregator.domain.model.Category

object RssFeedUrls {
    val feedsByCategory: Map<Category, List<String>> =
        mapOf(
            Category.WORLD to
                listOf(
                    "https://feeds.bbci.co.uk/news/world/rss.xml",
                    "https://rss.nytimes.com/services/xml/rss/nyt/World.xml",
                    "https://www.theguardian.com/world/rss",
                ),
            Category.TECH to
                listOf(
                    "https://feeds.arstechnica.com/arstechnica/index",
                    "https://www.theverge.com/rss/index.xml",
                    "https://techcrunch.com/feed/",
                ),
            Category.SPORTS to
                listOf(
                    "https://feeds.bbci.co.uk/sport/rss.xml",
                    "https://www.espn.com/espn/rss/news",
                ),
            Category.ENTERTAINMENT to
                listOf(
                    "https://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml",
                    "https://variety.com/feed/",
                ),
        )

    val allFeeds: List<Pair<Category, String>>
        get() =
            feedsByCategory.flatMap { (category, urls) ->
                urls.map { category to it }
            }
}
