package com.newsaggregator.presentation.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsaggregator.domain.model.Category
import com.newsaggregator.domain.usecase.GetArticlesUseCase
import com.newsaggregator.domain.usecase.RefreshArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsFeedViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val refreshArticlesUseCase: RefreshArticlesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(NewsFeedState())
    val state: StateFlow<NewsFeedState> = _state.asStateFlow()

    init {
        loadArticles()
        refreshIfNeeded()
    }

    fun onCategorySelected(category: Category) {
        _state.update { it.copy(selectedCategory = category) }
        loadArticles()
    }

    fun onRefresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }

            val result = refreshArticlesUseCase(_state.value.selectedCategory)

            _state.update {
                it.copy(
                    isRefreshing = false,
                    error = result.exceptionOrNull()?.message,
                )
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getArticlesUseCase(_state.value.selectedCategory)
                .catch { e ->
                    _state.update { it.copy(error = e.message, isLoading = false) }
                }.collect { articles ->
                    _state.update {
                        it.copy(articles = articles, isLoading = false)
                    }
                }
        }
    }

    private fun refreshIfNeeded() {
        viewModelScope.launch {
            // Initial refresh on app start
            if (_state.value.articles.isEmpty()) {
                onRefresh()
            }
        }
    }
}
