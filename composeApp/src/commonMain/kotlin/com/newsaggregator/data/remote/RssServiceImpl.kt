package com.newsaggregator.data.remote

import com.newsaggregator.domain.model.Category
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class RssServiceImpl(
    private val rssParser: RssParser,
) : RssService {
    private val limitedDispatcher = Dispatchers.IO.limitedParallelism(8)

    override suspend fun fetchFeed(url: String): Result<RssChannel> =
        runCatching {
            rssParser.getRssChannel(url)
        }

    override suspend fun fetchAllFeeds(): List<Pair<Category, Result<RssChannel>>> =
        coroutineScope {
            RssFeedUrls.allFeeds
                .map { (category, url) ->
                    async(limitedDispatcher) {
                        category to fetchFeed(url)
                    }
                }.awaitAll()
        }

    override suspend fun fetchFeedsByCategory(category: Category): List<Result<RssChannel>> =
        coroutineScope {
            val urls = RssFeedUrls.feedsByCategory[category] ?: emptyList()
            urls
                .map { url ->
                    async(limitedDispatcher) { fetchFeed(url) }
                }.awaitAll()
        }
}
