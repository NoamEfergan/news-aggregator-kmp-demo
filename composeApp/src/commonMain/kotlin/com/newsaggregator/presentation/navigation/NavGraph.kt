package com.newsaggregator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.newsaggregator.presentation.screens.detail.ArticleDetailScreen
import com.newsaggregator.presentation.screens.feed.NewsFeedScreen
import com.newsaggregator.presentation.screens.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Feed,
    ) {
        composable<Screen.Feed> {
            NewsFeedScreen(
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail(articleId))
                },
                onSearchClick = {
                    navController.navigate(Screen.Search)
                },
            )
        }

        composable<Screen.ArticleDetail> { backStackEntry ->
            val route: Screen.ArticleDetail = backStackEntry.toRoute()
            ArticleDetailScreen(
                articleId = route.articleId,
                onBackClick = { navController.popBackStack() },
            )
        }

        composable<Screen.Search> {
            SearchScreen(
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail(articleId))
                },
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
