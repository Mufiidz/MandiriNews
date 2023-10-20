package id.my.mufidz.mandirinews.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

abstract class BaseUseCase<RequestParam, ResponseObject, UseCaseResult> {

    protected abstract suspend fun execute(param: RequestParam?): ResponseObject

    protected abstract suspend fun ResponseObject.transformToResult(): UseCaseResult

    suspend fun getResult(param: RequestParam? = null): UseCaseResult =
        withContext(Dispatchers.IO) {
            execute(param).transformToResult()
        }
}