package id.my.mufidz.mandirinews.screen.search

import androidx.paging.PagingData
import id.my.mufidz.mandirinews.base.ActionResult
import id.my.mufidz.mandirinews.base.State
import id.my.mufidz.mandirinews.base.ViewAction
import id.my.mufidz.mandirinews.base.ViewState
import id.my.mufidz.mandirinews.model.Article
import id.my.mufidz.mandirinews.model.Search
import id.my.mufidz.mandirinews.usecase.TopHeadlineUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow

sealed class SearchAction : ViewAction {
    data class SearchNews(val search: String) : SearchAction()
    data class LastHistory(val query: String = "") : SearchAction()
    data class DeleteHistory(val search: Search) : SearchAction()
}

sealed class SearchResult : ActionResult {
    data class NewsEverything(val result: PagingData<Article>) : SearchResult()
    data class SearchHistory(val queries: List<Search>) : SearchResult()
}

data class SearchViewState(
    val errorMessage: String? = null,
    val news: MutableStateFlow<PagingData<Article>> = MutableStateFlow(PagingData.empty()),
    val searchHistories: List<Search> = listOf(),
    val scrollPosition: Int = 16,
    override val state: State = State.IDLE
) : ViewState