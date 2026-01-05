package com.newsaggregator.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsaggregator.domain.usecase.SearchArticlesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchArticlesUseCase: SearchArticlesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    _state.update { it.copy(isSearching = query.isNotEmpty()) }
                    searchArticlesUseCase(query)
                }.catch { /* Handle error silently */ }
                .collect { results ->
                    _state.update {
                        it.copy(results = results, isSearching = false)
                    }
                }
        }
    }

    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
        _searchQuery.value = query
    }

    fun clearQuery() {
        onQueryChange("")
    }
}
