package id.my.mufidz.mandirinews.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.mandirinews.base.BaseViewModel
import id.my.mufidz.mandirinews.base.State
import id.my.mufidz.mandirinews.usecase.NewsUseCase
import id.my.mufidz.mandirinews.usecase.TopHeadlineUseCase
import id.my.mufidz.mandirinews.usecase.TopHeadlineUseCaseResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val topHeadlineUseCase: TopHeadlineUseCase,
    private val newsUseCase: NewsUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeViewState, HomeAction, HomeResult>(HomeViewState()) {

    override fun HomeResult.updateViewState(): HomeViewState = when (this) {
        is HomeResult.NewsEverything -> updateState {
            it.copy(news = MutableStateFlow(result))
        }

        is HomeResult.TopHeadline -> result.mapResult()
        is HomeResult.ScrollPosition -> updateState {
            it.copy(scrollPosition = position)
        }
    }

    override fun HomeAction.handleAction(): Flow<HomeResult> = channelFlow {
        when (this@handleAction) {
            is HomeAction.LoadHomeData -> {
                delay(500)
                topHeadlineUseCase.getResult().also {
                    send(HomeResult.TopHeadline(it))
                }
                newsUseCase.getResult(search).also {
                    it.cachedIn(viewModelScope).collectLatest { data ->
                        send(HomeResult.NewsEverything(data))
                    }
                }
            }

            is HomeAction.ScrollPositon -> {
                val key = "scrollPosition"
                savedStateHandle[key] = position
                val savedPosition = savedStateHandle[key] ?: 0
                send(HomeResult.ScrollPosition(savedPosition))
            }
        }
    }

    private fun TopHeadlineUseCaseResult.mapResult(): HomeViewState = when (this) {
        is TopHeadlineUseCaseResult.Error -> updateState {
            it.copy(
                state = State.ERROR, errorMessage = message
            )
        }

        is TopHeadlineUseCaseResult.Success -> updateState {
            it.copy(
                state = State.IDLE, article = article
            )
        }
    }
}