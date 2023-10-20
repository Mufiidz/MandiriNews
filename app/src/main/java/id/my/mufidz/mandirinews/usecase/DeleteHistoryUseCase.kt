package id.my.mufidz.mandirinews.usecase

import id.my.mufidz.mandirinews.base.BaseUseCase
import id.my.mufidz.mandirinews.data.local.SearchDao
import id.my.mufidz.mandirinews.model.Search
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(private val searchDao: SearchDao) : BaseUseCase<Search, Unit, Unit>() {
    override suspend fun execute(param: Search?) {
        if (param == null) return
        return searchDao.deleteHistory(param)
    }

    override suspend fun Unit.transformToResult() {
        return
    }
}