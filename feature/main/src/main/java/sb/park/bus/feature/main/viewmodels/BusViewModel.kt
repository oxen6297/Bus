package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.data.repository.BusIdRepository
import sb.park.bus.data.repository.BusStationRepository
import sb.park.bus.data.response.BusIdResponse
import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.data.ApiResult
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busIdRepository: BusIdRepository,
    private val busStationRepository: BusStationRepository
) : ViewModel() {

    private val _busIdFlow = MutableStateFlow<ApiResult<List<BusIdResponse>>>(ApiResult.Loading)
    val busIdFlow = _busIdFlow.asStateFlow()

    private val _stationFlow =
        MutableStateFlow<ApiResult<List<BusSearchResponse>>>(ApiResult.Loading)
    val stationFlow = _stationFlow.asStateFlow()

    fun getBusList(busNumber: String) {
        viewModelScope.launch {
            busIdRepository.getData(busNumber).collectLatest {
                _busIdFlow.emit(it)
            }
        }
    }

    fun getStationList(busId: String) {
        viewModelScope.launch {
            busStationRepository.getSearch(busId).collectLatest {
                _stationFlow.emit(it)
            }
        }
    }
}