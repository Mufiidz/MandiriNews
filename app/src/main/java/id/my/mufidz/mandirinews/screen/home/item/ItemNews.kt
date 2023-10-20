package id.my.mufidz.mandirinews.screen.home.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.mufidz.mandirinews.base.components.NetworkImage
import id.my.mufidz.mandirinews.model.Article

@Composable
fun ItemNews(news: Article, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val cornerImage = RoundedCornerShape(15.dp)
    Box(
        modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .weight(0.3f),
            ) {
                NetworkImage(
                    url = news.urlToImage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(cornerImage),
                    placeHolderModifier = Modifier.background(Color.LightGray, cornerImage),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(0.7f), verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = news.title,
                        modifier = Modifier.padding(bottom = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Clip
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Icon person",
                                modifier = Modifier.size(16.dp),
                            )
                            Text(
                                text = news.author,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                modifier = Modifier.size(16.dp),
                                contentDescription = "Icon person"
                            )
                            Text(
                                text = news.formattedDate(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}