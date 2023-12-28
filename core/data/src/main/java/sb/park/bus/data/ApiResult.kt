package sb.park.bus.data

sealed interface ApiResult<out T> {
    object Loading : ApiResult<Nothing>
    data class Success<out T>(val data: T) : ApiResult<T>
    data class Error(val e: Throwable) : ApiResult<Nothing>
}

inline fun <reified T> ApiResult<T>.onLoading(loading: () -> Unit) {
    if (this is ApiResult.Loading) {
        loading()
    }
}

inline fun <reified T> ApiResult<T>.onSuccess(success: (data: T) -> Unit) {
    if (this is ApiResult.Success) {
        success(data)
    }
}

inline fun <reified T> ApiResult<T>.onError(failed: (Throwable) -> Unit) {
    if (this is ApiResult.Error) {
        failed(e)
    }
}