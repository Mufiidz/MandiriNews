package id.my.mufidz.mandirinews.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.my.mufidz.mandirinews.base.ActionResult
import id.my.mufidz.mandirinews.base.BaseUseCase
import id.my.mufidz.mandirinews.data.network.NewsApiResponse
import id.my.mufidz.mandirinews.data.paging.NewsPagingSource
import id.my.mufidz.mandirinews.model.Article
import id.my.mufidz.mandirinews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val newsRepository: NewsRepository) :
    BaseUseCase<String, Flow<PagingData<Article>>, Flow<PagingData<Article>>>() {

    override suspend fun execute(param: String?): Flow<PagingData<Article>> {
        if (param.isNullOrEmpty()) {
            return MutableStateFlow(PagingData.empty())
        }
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsRepository,
                    NewsPagingSource.Param(param)
                )
            },
            initialKey = 1
        ).flow
    }

    override suspend fun Flow<PagingData<Article>>.transformToResult(): Flow<PagingData<Article>> =
        this.distinctUntilChanged()

}