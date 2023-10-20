package id.my.mufidz.mandirinews.base.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    placeHolderModifier: Modifier = modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (url.isEmpty()) {
        Box(modifier.then(placeHolderModifier)) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Image Available",
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    CoilImage(
        imageModel = { url },
        modifier = modifier,
        imageOptions = ImageOptions(contentScale = contentScale),
        component = rememberImageComponent {
            +CrossfadePlugin(
                duration = 500
            )
            +ShimmerPlugin(
                baseColor = Color.Gray, highlightColor = Color.LightGray
            )
        }
    )
}