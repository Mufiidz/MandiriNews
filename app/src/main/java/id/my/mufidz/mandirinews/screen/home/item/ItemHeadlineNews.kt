package id.my.mufidz.mandirinews.screen.home.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mandirinews.base.components.NetworkImage
import id.my.mufidz.mandirinews.model.Article
import id.my.mufidz.mandirinews.screen.destinations.DetailNewsScreenDestination

@Composable
fun ItemHeadlineNews(
    news: Article,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .wrapContentHeight()
        .clickable(onClick = onClick)
    ) {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 16.dp)
        ) {
            NetworkImage(
                url = news.urlToImage,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .padding(bottom = 8.dp),
                placeHolderModifier = Modifier
                    .height(200.dp)
                    .background(Color.LightGray, RoundedCornerShape(20.dp))
            )
            Text(
                text = news.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Clip,
                fontSize = 18.sp
            )
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = news.author, maxLines = 2)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = news.formattedDate(), maxLines = 1)
                }
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//private fun ItemHeadlineNewsPreview() {
//    MandiriNewsTheme {
//        ItemHeadlineNews(news = Article(title = "", publishedAt = "Tanggal", author = "Author"))
//    }
//}