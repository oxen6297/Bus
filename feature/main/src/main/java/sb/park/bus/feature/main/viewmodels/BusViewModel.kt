package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.MutableLiveData
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
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busIdRepository: BusIdRepository,
    private val busStationRepository: BusStationRepository
) : ViewModel() {
    val busNumber = MutableLiveData<String>()

    private val _uiState = MutableStateFlow<ApiResult<Any>>(ApiResult.Loading)
    val uiState = _uiState.asStateFlow()

    private val _busStationFlow = MutableStateFlow<List<BusSearchResponse>?>(emptyList())
    val busStationFlow = _busStationFlow.asStateFlow()

    fun fetchUiState() {
        viewModelScope.launch {
            busIdRepository.getData(busNumber.value ?: "").collectLatest { idState ->
                if (idState is ApiResult.Error) _uiState.emit(ApiResult.Error(idState.e))
                if (idState is ApiResult.Success) {
                    idState.data.forEach { response ->
                        busStationRepository.getSearch(response.routeId.toString()).collectLatest { searchState ->
                            if (searchState is ApiResult.Error) _uiState.emit(ApiResult.Error(searchState.e))
                            if (searchState is ApiResult.Success){
                                _busStationFlow.emit(searchState.data)
                                _uiState.emit(ApiResult.Success(searchState.data))
                            }
                        }
                    }
                }
            }
        }
    }
}