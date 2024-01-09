package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.data.ApiResult
import sb.park.bus.data.repository.BusIdRepository
import sb.park.bus.data.repository.BusStationRepository
import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.data.successOrNull
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busIdRepository: BusIdRepository,
    private val busStationRepository: BusStationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ApiResult<Any>>(ApiResult.Loading)
    val uiState = _uiState.asStateFlow()

    private val _busStationFlow = MutableStateFlow<List<BusSearchResponse>?>(emptyList())
    val busStationFlow = _busStationFlow.asStateFlow()

    fun getData(text: String) {
        viewModelScope.launch {
            busIdRepository.getData(text).collectLatest { idState ->
                when (idState) {
                    is ApiResult.Loading -> _uiState.emit(ApiResult.Loading)
                    is ApiResult.Error -> _uiState.emit(ApiResult.Error(idState.e))
                    is ApiResult.Success -> {
                        if (idState.successOrNull().isNullOrEmpty()) {
                            _busStationFlow.emit(emptyList())
                            _uiState.emit(ApiResult.Success(null))
                        }
                        idState.successOrNull()?.forEach { response ->
                            busStationRepository.getSearch(response.routeId.toString())
                                .collectLatest { searchState ->
                                    if (searchState is ApiResult.Error) {
                                        _uiState.emit(ApiResult.Error(searchState.e))
                                    }
                                    if (searchState is ApiResult.Success) {
                                        _busStationFlow.emit(searchState.successOrNull())
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