package id.my.mufidz.mandirinews.base.components

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyListScope.itemsPaging(
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T) -> Unit
) {
    items(
        count = items.itemCount,
    ) { index ->
        val item = items[index]
        item?.let { itemContent(it) }
    }
}