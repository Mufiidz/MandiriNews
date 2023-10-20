package id.my.mufidz.mandirinews.data.network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface NewsAPIServices {
    @GET(Endpoints.TOP_HEADLINES)
    suspend fun topHeadlines(
        @Query(Endpoints.COUNTRY) country: String? = "en",
        @Query(Endpoints.PAGE) page: Int? = 1,
        @Query(Endpoints.PAGE_SIZE) pageSize: Int? = 20
    ) : NewsApiResponse

    @GET(Endpoints.EVERYTHING)
    suspend fun everything(
        @Query("q") query: String? = null,
        @Query(Endpoints.LANGUAGE) language: String? = "en",
        @Query(Endpoints.PAGE) page: Int? = 1,
        @Query(Endpoints.PAGE_SIZE) pageSize: Int? = 20,
        @Query(Endpoints.SORT_BY) sortBy: String? = "publishedAt"
    ) : NewsApiResponse
}