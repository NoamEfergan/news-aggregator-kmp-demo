package com.newsaggregator.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.newsaggregator.db.NewsDatabase

actual class DatabaseDriverFactory(
    private val context: Context,
) {
    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(NewsDatabase.Schema, context, "news.db")
}
