package id.my.mufidz.mandirinews.screen.home.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mandirinews.model.Article
import id.my.mufidz.mandirinews.screen.destinations.DetailNewsScreenDestination
import id.my.mufidz.mandirinews.screen.home.item.ItemHeadlineNews

@Composable
fun TopHeadlineSection(article: Article, navigator: DestinationsNavigator) {
    Column(Modifier.fillMaxWidth().wrapContentHeight()) {
        Text(text = "Berita Terkini", modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp))
        ItemHeadlineNews(news = article) {
            navigator.navigate(DetailNewsScreenDestination(article))
        }
    }
}