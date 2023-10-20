package id.my.mufidz.mandirinews.screen.home

import androidx.paging.PagingData
import id.my.mufidz.mandirinews.base.ActionResult
import id.my.mufidz.mandirinews.base.State
import id.my.mufidz.mandirinews.base.ViewAction
import id.my.mufidz.mandirinews.base.ViewState
import id.my.mufidz.mandirinews.model.Article
import id.my.mufidz.mandirinews.usecase.TopHeadlineUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow

sealed class HomeAction : ViewAction {
    data class LoadHomeData(val search: String) : HomeAction()
    data class ScrollPositon(val position: Int) : HomeAction()
}

sealed class HomeResult : ActionResult {
    data class TopHeadline(val result: TopHeadlineUseCaseResult) : HomeResult()
    data class NewsEverything(val result: PagingData<Article>) : HomeResult()
    data class ScrollPosition(val position: Int) : HomeResult()
}

data class HomeViewState(
    val errorMessage: String? = null,
    val news: MutableStateFlow<PagingData<Article>> = MutableStateFlow(PagingData.empty()),
    val article: Article = Article(),
    val scrollPosition: Int = 16,
    override val state: State = State.LOADING
) : ViewState