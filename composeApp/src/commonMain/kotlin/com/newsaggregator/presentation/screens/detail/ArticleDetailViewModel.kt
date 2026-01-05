package com.newsaggregator.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsaggregator.domain.usecase.GetArticleByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    private val articleId: String,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ArticleDetailState())
    val state: StateFlow<ArticleDetailState> = _state.asStateFlow()

    init {
        loadArticle()
    }

    private fun loadArticle() {
        viewModelScope.launch {
            getArticleByIdUseCase(articleId)
                .catch { e ->
                    _state.update {
                        it.copy(error = e.message, isLoading = false)
                    }
                }.collect { article ->
                    _state.update {
                        it.copy(article = article, isLoading = false)
                    }
                }
        }
    }
}
