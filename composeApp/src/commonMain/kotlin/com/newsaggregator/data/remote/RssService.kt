package com.newsaggregator.data.remote

import com.newsaggregator.domain.model.Category
import com.prof18.rssparser.model.RssChannel

interface RssService {
    suspend fun fetchFeed(url: String): Result<RssChannel>

    suspend fun fetchAllFeeds(): List<Pair<Category, Result<RssChannel>>>

    suspend fun fetchFeedsByCategory(category: Category): List<Result<RssChannel>>
}
