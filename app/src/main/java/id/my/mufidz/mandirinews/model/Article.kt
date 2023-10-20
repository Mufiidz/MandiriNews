package id.my.mufidz.mandirinews.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
@Parcelize
data class Article(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: Source = Source(),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
) : Parcelable {

    private fun datePublishedAt(): LocalDateTime = LocalDateTime.parse(publishedAt, DateTimeFormatter.ISO_DATE_TIME)

    fun formattedDate(): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
            datePublishedAt().format(formatter)
        } catch (e: Exception) {
            "-"
        }
    }
}