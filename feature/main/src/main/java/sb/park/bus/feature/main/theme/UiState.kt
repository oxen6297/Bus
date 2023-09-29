package sb.park.bus.feature.main.theme

sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val e: Throwable) : UiState<Nothing>
}