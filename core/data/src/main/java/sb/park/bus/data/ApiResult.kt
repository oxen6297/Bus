package sb.park.bus.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val e: Throwable) : ApiResult<Nothing>()
}

inline fun <reified T> ApiResult<T>.onLoading(loading: () -> Unit) {
    if (this is ApiResult.Loading) {
        loading()
    }
}

inline fun <reified T> ApiResult<T>.onSuccess(action: (data: T) -> Unit) {
    if (this is ApiResult.Success) {
        action(data)
    }
}

inline fun <reified T> ApiResult<T>.onError(error: (Throwable) -> Unit) {
    if (this is ApiResult.Error) {
        error(e)
    }
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

internal fun <T> safeFlow(service: suspend () -> T): Flow<ApiResult<T>> = flow {
    try {
        emit(ApiResult.Success(service()))
    } catch (e: Exception) {
        emit(ApiResult.Error(e))
    }
}.onStart {
    emit(ApiResult.Loading)
}