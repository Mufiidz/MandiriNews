package id.my.mufidz.mandirinews.base.components

import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(url: String, modifier: Modifier = Modifier, isLoadingPage: (Boolean) -> Unit = {}) {
    isLoadingPage(true)
    AndroidView(factory = {
        WebView(it).apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    isLoadingPage(true)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    isLoadingPage(false)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    isLoadingPage(false)
                }
            }
        }
    }, modifier)
}