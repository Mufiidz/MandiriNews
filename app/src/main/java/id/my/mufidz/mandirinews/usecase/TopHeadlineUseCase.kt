package id.my.mufidz.mandirinews.usecase

import id.my.mufidz.mandirinews.base.ActionResult
import id.my.mufidz.mandirinews.base.BaseUseCase
import id.my.mufidz.mandirinews.data.network.NewsApiResponse
import id.my.mufidz.mandirinews.model.Article
import id.my.mufidz.mandirinews.repository.NewsRepository
import javax.inject.Inject

class TopHeadlineUseCase @Inject constructor(private val newsRepository: NewsRepository) :
    BaseUseCase<Nothing, NewsApiResponse, TopHeadlineUseCaseResult>() {
    override suspend fun execute(param: Nothing?): NewsApiResponse {
        return newsRepository.getTopHeadlines()
    }

    override suspend fun NewsApiResponse.transformToResult(): TopHeadlineUseCaseResult =
        when (this) {
            is NewsApiResponse.ErrorResponse -> TopHeadlineUseCaseResult.Error(message)
            is NewsApiResponse.SuccessResponse -> TopHeadlineUseCaseResult.Success(articles.first())
        }

}

sealed class TopHeadlineUseCaseResult : ActionResult {
    data class Success(val article: Article) : TopHeadlineUseCaseResult()
    data class Error(val message: String) : TopHeadlineUseCaseResult()
}