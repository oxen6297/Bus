package sb.park.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()
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

fun <T> safeFlow(service: suspend () -> T): Flow<ApiResult<T>> = flow {
    runCatching {
        emit(ApiResult.Success(service()))
    }.getOrElse {
        emit(ApiResult.Error(it))
    }
}.onStart {
    emit(ApiResult.Loading)
}