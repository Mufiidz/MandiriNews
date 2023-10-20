package id.my.mufidz.mandirinews.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.my.mufidz.mandirinews.data.network.NewsApiResponse
import id.my.mufidz.mandirinews.model.Article
import id.my.mufidz.mandirinews.repository.NewsRepository

class NewsPagingSource (private val newsRepository: NewsRepository, private val param: Param) :
    PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        if (param.search.isEmpty()) {
            return LoadResult.Error(Exception("Search is empty"))
        }
        val currentPage = params.key ?: 0
        return try {
            when (val response = newsRepository.getNews(currentPage, param.search)) {
                is NewsApiResponse.ErrorResponse -> {
                    LoadResult.Error(Exception(response.message))
                }

                is NewsApiResponse.SuccessResponse -> {
                    val news = response.articles
                    LoadResult.Page(
                        data = news,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (news.isEmpty()) null else currentPage + 1
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    data class Param(val search: String)
}