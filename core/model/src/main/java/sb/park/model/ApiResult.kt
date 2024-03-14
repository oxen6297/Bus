package sb.park.model

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<out T>(val data: T?) : ApiResult<T>()
    data class Error(val e: Throwable) : ApiResult<Nothing>()
}

fun <T> ApiResult<T>.throwableOrNull(): Throwable? = if (this is ApiResult.Error) {
    e
} else {
    null
}

fun <T> ApiResult<T>.successOrNull(): T? = if (this is ApiResult.Success<T>) {
    data
} else {
    null
}

inline fun <T> safeFlow(crossinline service: suspend () -> T): Flow<ApiResult<T>> = flow<ApiResult<T>> {
    emit(ApiResult.Success(service()))
}.onStart {
    emit(ApiResult.Loading)
}.catch {
    it.printStackTrace()
    emit(ApiResult.Error(it))
}