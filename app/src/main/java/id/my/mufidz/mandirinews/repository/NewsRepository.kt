package id.my.mufidz.mandirinews.repository

import id.my.mufidz.mandirinews.data.network.NewsAPIServices
import id.my.mufidz.mandirinews.data.network.NewsApiResponse
import javax.inject.Inject

interface NewsRepository {
    suspend fun getTopHeadlines(): NewsApiResponse
    suspend fun getNews(page: Int?, search: String?): NewsApiResponse
}

class NewsRepositoryImpl @Inject constructor(
    private val newsAPIServices: NewsAPIServices
) : NewsRepository {

    override suspend fun getTopHeadlines(): NewsApiResponse =
        newsAPIServices.topHeadlines(country = "id")

    override suspend fun getNews(page: Int?, search: String?): NewsApiResponse =
        when (val response = newsAPIServices.everything(search, page = page ?: 1)) {
            is NewsApiResponse.ErrorResponse -> response
            is NewsApiResponse.SuccessResponse -> {
                val newArticles = response.articles.map {
                    it.copy(author = it.author.ifEmpty { it.source.name.ifEmpty { "-" } })
                }.filterNot { it.title.contains("[Removed]", ignoreCase = true) }
                response.copy(articles = newArticles)
            }
        }
}