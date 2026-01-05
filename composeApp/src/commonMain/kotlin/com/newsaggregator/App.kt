package com.newsaggregator

import androidx.compose.runtime.Composable
import com.newsaggregator.presentation.navigation.NavGraph
import com.newsaggregator.presentation.theme.NewsAggregatorTheme

@Composable
fun App() {
    NewsAggregatorTheme {
        NavGraph()
    }
}
