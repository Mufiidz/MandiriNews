package id.my.mufidz.mandirinews.utils

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import id.my.mufidz.mandirinews.data.network.NewsApiResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class NewsApiResponseAdapterFactory : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData, ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != NewsApiResponse::class) return null
        return object : Converter.SuspendResponseConverter<HttpResponse, NewsApiResponse> {
            override suspend fun convert(response: HttpResponse): NewsApiResponse {
                val status = response.status
                if (status == HttpStatusCode.OK) return response.body<NewsApiResponse.SuccessResponse>()
                return try {
                    val errorResponse = response.body<NewsApiResponse.ErrorResponse>()
                    if (errorResponse.status.contains("maximumResultsReached", ignoreCase = true)) {
                        NewsApiResponse.SuccessResponse(
                            errorResponse.status,
                            totalResults = 0,
                            emptyList()
                        )
                    }
                    errorResponse
                } catch (e: Exception) {
                    NewsApiResponse.ErrorResponse(
                        status.value.toString(), e.localizedMessage ?: "Error cant convert"
                    )
                }
            }
        }
    }
}