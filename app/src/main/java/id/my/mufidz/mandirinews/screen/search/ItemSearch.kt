package id.my.mufidz.mandirinews.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.my.mufidz.mandirinews.model.Search

@Composable
fun ItemSearch(search: Search, onDelete: () -> Unit = {}, onClick: () -> Unit = {}) {
    ListItem(headlineContent = { Text(text = search.query) }, leadingContent = {
        Icon(
            imageVector = Icons.Default.History, contentDescription = "last search"
        )
    }, modifier = Modifier.clickable(onClick = onClick), trailingContent = {
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Delete")
        }
    })
}