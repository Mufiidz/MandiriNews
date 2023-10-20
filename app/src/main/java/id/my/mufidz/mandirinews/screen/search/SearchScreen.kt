package id.my.mufidz.mandirinews.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mandirinews.base.State
import id.my.mufidz.mandirinews.base.components.itemsPaging
import id.my.mufidz.mandirinews.screen.destinations.DetailNewsScreenDestination
import id.my.mufidz.mandirinews.screen.home.item.ItemNews
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SearchScreen(navigator: DestinationsNavigator) {
    val searchViewModel = hiltViewModel<SearchViewModel>()
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(true) }
    val state = searchViewModel.viewState.collectAsState().value
    val newsPagingData = state.news.collectAsLazyPagingItems()
    val pagingState = newsPagingData.loadState
    val statePrepend = pagingState.prepend

    searchViewModel.execute(SearchAction.LastHistory(query))

    Scaffold(topBar = {
        DockedSearchBar(query = query,
            onQueryChange = {
                query = it
            },
            onSearch = {
                query = it
                active = false
                searchViewModel.execute(SearchAction.SearchNews(it))
            },
            active = active,
            onActiveChange = { active = it },
            shape = RectangleShape,
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                if (active) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                } else {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            },
            trailingIcon = {
                if (active) {
                    IconButton(onClick = {
                        if (query.isNotEmpty()) query = "" else active = false
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            }) {
            if (state.searchHistories.isEmpty()) {
                Text(text = "Empty search history", modifier = Modifier.padding(16.dp))
            }
            state.searchHistories.forEach {
                ItemSearch(
                    search = it,
                    onDelete = { searchViewModel.execute(SearchAction.DeleteHistory(it)) }) {
                    query = it.query
                    searchViewModel.execute(SearchAction.LastHistory(query))
                    active = !active

                }
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
                if (newsPagingData.itemCount == 0) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No Data")
                    }
                }
                LazyColumn(
                    Modifier.padding(paddingValues), contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    itemsPaging(newsPagingData) {
                        ItemNews(news = it) {
                            navigator.navigate(DetailNewsScreenDestination(it))
                        }
                    }
                    item {
                        Column(
                            Modifier.fillMaxWidth(),
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
