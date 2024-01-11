package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.data.ApiResult
import sb.park.bus.data.repository.BusIdRepository
import sb.park.bus.data.response.BusIdResponse
import sb.park.bus.data.successOrNull
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(private val busIdRepository: BusIdRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ApiResult<List<BusIdResponse>>>(ApiResult.Loading)
    val uiState: StateFlow<ApiResult<List<BusIdResponse>>>
        get() = _uiState

    private val _busIdFlow = MutableStateFlow<List<BusIdResponse>?>(null)
    val busIdFlow: StateFlow<List<BusIdResponse>?>
        get() = _busIdFlow

    fun getSearch(busNumber: String) {
        viewModelScope.launch {
            busIdRepository.getData(busNumber).collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> _uiState.emit(ApiResult.Loading)
                    is ApiResult.Success -> {
                        _uiState.emit(ApiResult.Success(result.successOrNull()))
                        _busIdFlow.emit(result.successOrNull())
                    }

                    is ApiResult.Error -> _uiState.emit(ApiResult.Error(result.e))
                }
            }
        }
    }
}