package id.my.mufidz.mandirinews.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mandirinews.base.components.WebView
import id.my.mufidz.mandirinews.model.Article

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
fun DetailNewsScreen(navigator: DestinationsNavigator, news: Article) {
    var isLoading by remember { mutableStateOf(true) }
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = news.title, maxLines = 1, modifier = Modifier.basicMarquee()
                )
            }, navigationIcon = {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button"
                    )
                }
            })
        },
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = isLoading, modifier = Modifier.fillMaxWidth()) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }
            WebView(
                url = news.url, modifier = Modifier.fillMaxSize()
            ) { isLoading = it }
        }
    }
}