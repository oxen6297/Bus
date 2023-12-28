package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sb.park.bus.data.repository.BusIdRepository
import sb.park.bus.data.repository.BusStationRepository
import sb.park.bus.data.response.BusIdResponse
import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.feature.main.ApiResult
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busIdRepository: BusIdRepository,
    private val busStationRepository: BusStationRepository
) : ViewModel() {

    private val _bus = MutableStateFlow<ApiResult<List<BusIdResponse>>>(ApiResult.Loading)
    val bus = _bus.asStateFlow()

    private val _station = MutableStateFlow<ApiResult<List<BusSearchResponse>>>(ApiResult.Loading)
    val station = _station.asStateFlow()

    fun getBusList(busNumber: String) {
        viewModelScope.launch {
            try {
                _bus.emit(ApiResult.Success(busIdRepository.getData(busNumber)))
            } catch (e: Exception) {
                _bus.emit(ApiResult.Error(e))
            }
        }
    }

    fun getStationList(busId: String) {
        viewModelScope.launch {
            try {
                _station.emit(ApiResult.Success(busStationRepository.getSearch(busId)))
            } catch (e: Exception) {
                _station.emit(ApiResult.Error(e))
            }
        }
    }
}