package id.my.mufidz.mandirinews.usecase

import id.my.mufidz.mandirinews.base.BaseUseCase
import id.my.mufidz.mandirinews.data.local.SearchDao
import id.my.mufidz.mandirinews.model.Search
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(private val searchDao: SearchDao) :
    BaseUseCase<String, List<Search>, List<Search>>() {
    override suspend fun execute(param: String?): List<Search> {
        if (param.isNullOrEmpty()) {
            return searchDao.getLastSearch()
        }
        return searchDao.searchHistory("%$param%")
    }

    override suspend fun List<Search>.transformToResult(): List<Search> = this
}