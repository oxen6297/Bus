package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.domain.usecases.BusIdUseCase
import sb.park.domain.usecases.BusSearchUseCase
import sb.park.model.ApiResult
import sb.park.model.response.BusIdResponse
import sb.park.model.response.BusSearchResponse
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val busIdUseCase: BusIdUseCase,
    private val busSearchUseCase: BusSearchUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ApiResult<List<BusSearchResponse>>>(ApiResult.Success(emptyList()))
    val uiState = _uiState.asStateFlow()

    val busFlow = _uiState.map { it.successOrNull() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    @OptIn(FlowPreview::class)
    fun getData(text: String) {
        viewModelScope.launch {
            busIdUseCase(text).debounce(500).collectLatest { idState ->
                when (idState) {
                    is ApiResult.Loading -> _uiState.emit(ApiResult.Loading)
                    is ApiResult.Error -> _uiState.emit(ApiResult.Error(idState.e))
                    is ApiResult.Success -> {
                        if (idState.successOrNull().isNullOrEmpty()) {
                            _uiState.emit(ApiResult.Success(emptyList()))
                        } else {
                            getSearch(idState.successOrNull())
                        }
                    }
                }
            }
        }
    }

    private suspend fun getSearch(idList: List<BusIdResponse>?) {
        val searchList: MutableList<BusSearchResponse> = mutableListOf()

        idList?.forEach {
            busSearchUseCase(it.routeId.toString()).collectLatest { searchState ->
                when (searchState) {
                    is ApiResult.Loading -> {}
                    is ApiResult.Error -> _uiState.emit(ApiResult.Error(searchState.e))
                    is ApiResult.Success -> {
                        searchList.addAll(searchState.successOrNull() ?: emptyList())
                    }
                }
            }
        }

        _uiState.emit(ApiResult.Success(searchList))
    }
}