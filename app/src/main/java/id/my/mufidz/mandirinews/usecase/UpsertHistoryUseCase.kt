package id.my.mufidz.mandirinews.usecase

import id.my.mufidz.mandirinews.base.BaseUseCase
import id.my.mufidz.mandirinews.data.local.SearchDao
import id.my.mufidz.mandirinews.model.Search
import javax.inject.Inject

class UpsertHistoryUseCase @Inject constructor(private val searchDao: SearchDao) : BaseUseCase<String, Unit, Unit>()  {
    override suspend fun execute(param: String?) {
        if (param.isNullOrEmpty()) return
        return searchDao.upsertSearch(Search(param))
    }

    override suspend fun Unit.transformToResult() {
        return
    }
}