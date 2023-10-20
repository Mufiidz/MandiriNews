package id.my.mufidz.mandirinews.data.network

import id.my.mufidz.mandirinews.model.Article
import kotlinx.serialization.Serializable

sealed class NewsApiResponse {
    @Serializable
    data class SuccessResponse(
        val status: String = "",
        val totalResults: Int = 1,
        val articles: List<Article> = emptyList()
    ) : NewsApiResponse()

    @Serializable
    data class ErrorResponse(
        val code: String = "", val message: String = "", val status: String = ""
    ) : NewsApiResponse()
}
