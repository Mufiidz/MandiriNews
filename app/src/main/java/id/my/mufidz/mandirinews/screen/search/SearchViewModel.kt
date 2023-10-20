package id.my.mufidz.mandirinews.screen.search

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.mandirinews.base.BaseViewModel
import id.my.mufidz.mandirinews.data.local.SearchDao
import id.my.mufidz.mandirinews.model.Search
import id.my.mufidz.mandirinews.usecase.DeleteHistoryUseCase
import id.my.mufidz.mandirinews.usecase.NewsUseCase
import id.my.mufidz.mandirinews.usecase.SearchHistoryUseCase
import id.my.mufidz.mandirinews.usecase.UpsertHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val upsertHistoryUseCase: UpsertHistoryUseCase,
    private val searchHistoryUseCase: SearchHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
) : BaseViewModel<SearchViewState, SearchAction, SearchResult>(
    SearchViewState()
) {
    override fun SearchResult.updateViewState(): SearchViewState = when (this) {
        is SearchResult.NewsEverything -> updateState {
            Timber.d(result.toString())
            it.copy(news = MutableStateFlow(result))
        }

        is SearchResult.SearchHistory -> updateState {
            it.copy(searchHistories = queries)
        }
    }

    @OptIn(FlowPreview::class)
    override fun SearchAction.handleAction(): Flow<SearchResult> = channelFlow {
        when (this@handleAction) {
            is SearchAction.SearchNews -> {
                snapshotFlow { search }.debounce(1000L).collectLatest { newSearch ->
                    upsertHistoryUseCase.getResult(newSearch)
                    execute(SearchAction.LastHistory(newSearch))

                    newsUseCase.getResult(newSearch).also {
                        it.cachedIn(viewModelScope).collectLatest { data ->
                            send(SearchResult.NewsEverything(data))
                        }
                    }
                }
            }

            is SearchAction.DeleteHistory -> {
                deleteHistoryUseCase.getResult(search)
                execute(SearchAction.LastHistory())
            }

            is SearchAction.LastHistory -> {
                searchHistoryUseCase.getResult(query).also {
                    send(SearchResult.SearchHistory(it))
                }
            }
        }
    }
}