package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.domain.usecases.BusIdUseCase
import sb.park.domain.usecases.BusSearchUseCase
import sb.park.model.ApiResult
import sb.park.model.response.BusSearchResponse
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val busIdUseCase: BusIdUseCase,
    private val busSearchUseCase: BusSearchUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ApiResult<Any>>(ApiResult.Success(null))
    val uiState = _uiState.asStateFlow()

    private val _busData = MutableStateFlow<List<BusSearchResponse>?>(emptyList())
    val busData = _busData.asStateFlow()

    fun getData(text: String) {
        viewModelScope.launch {
            busIdUseCase(text).collectLatest { idState ->
                when (idState) {
                    is ApiResult.Loading -> _uiState.emit(ApiResult.Loading)
                    is ApiResult.Error -> _uiState.emit(ApiResult.Error(idState.e))
                    is ApiResult.Success -> {
                        if (idState.successOrNull().isNullOrEmpty()) {
                            _busData.emit(emptyList())
                            _uiState.emit(ApiResult.Success(null))
                        }
                        idState.successOrNull()?.forEach { response ->
                            busSearchUseCase(response.routeId.toString())
                                .collectLatest { searchState ->
                                    if (searchState is ApiResult.Error) {
                                        _uiState.emit(ApiResult.Error(searchState.e))
                                    }
                                    if (searchState is ApiResult.Success) {
                                        _busData.emit(searchState.successOrNull())
                                        _uiState.emit(ApiResult.Success(searchState.data))
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}