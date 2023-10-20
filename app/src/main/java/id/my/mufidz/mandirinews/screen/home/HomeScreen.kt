package id.my.mufidz.mandirinews.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mandirinews.R
import id.my.mufidz.mandirinews.base.State
import id.my.mufidz.mandirinews.base.components.itemsPaging
import id.my.mufidz.mandirinews.screen.destinations.DetailNewsScreenDestination
import id.my.mufidz.mandirinews.screen.destinations.SearchScreenDestination
import id.my.mufidz.mandirinews.screen.home.item.ItemNews
import id.my.mufidz.mandirinews.screen.home.section.TopHeadlineSection
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    homeViewModel.execute(HomeAction.LoadHomeData("Wealth"))
    HomeScreenContent(homeViewModel, navigator)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(homeViewModel: HomeViewModel, navigator: DestinationsNavigator) {
    val state = homeViewModel.viewState.collectAsState().value
    val snackbarHost = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var isScrollToTop by remember { mutableStateOf(false) }
    val newsPagingData = state.news.collectAsLazyPagingItems()
    val pagingState = newsPagingData.loadState
    val statePrepend = pagingState.prepend
    val listState = rememberLazyListState(state.scrollPosition)

    isScrollToTop = (state.scrollPosition > 2) && !listState.isScrollInProgress

    DisposableEffect(listState) {
        onDispose {
            homeViewModel.execute(HomeAction.ScrollPositon(listState.firstVisibleItemIndex))
        }
    }

    Scaffold(Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navigator.navigate(SearchScreenDestination) }) {
                        Icon(Icons.Default.Search, "Search")
                    }
                })
        },
        snackbarHost = { SnackbarHost(snackbarHost) },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isScrollToTop,
                enter = slideInVertically { it },
            ) {
                SmallFloatingActionButton(onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Icon Scroll to top"
                    )
                }
            }
        }) { paddingValues ->
        when {
            statePrepend is LoadState.Error -> {
                val error = statePrepend.error
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = error.localizedMessage ?: error.toString())
                    TextButton(onClick = { newsPagingData.retry() }) {
                        Text(text = "Retry")
                    }
                }
            }

            statePrepend is LoadState.Loading || state.state == State.LOADING -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            statePrepend is LoadState.NotLoading -> {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    state = listState
                ) {
                    item { TopHeadlineSection(state.article, navigator) }
                    item {
                        Text(
                            text = "Semua Berita",
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                    itemsPaging(newsPagingData) {
                        ItemNews(news = it) {
                            navigator.navigate(DetailNewsScreenDestination(it))
                        }
                    }
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            when (val stateAppend = pagingState.append) {
                                is LoadState.Error -> {
                                    val error = stateAppend.error
                                    Text(text = error.localizedMessage ?: error.toString())
                                    TextButton(onClick = { newsPagingData.retry() }) {
                                        Text(text = "Retry")
                                    }
                                }

                                LoadState.Loading -> {
                                    CircularProgressIndicator(Modifier.padding(8.dp))
                                }

                                is LoadState.NotLoading -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}