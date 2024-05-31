package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sb.park.domain.usecases.LocationUseCase
import sb.park.domain.usecases.NearStationListUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.GPSModel
import sb.park.model.response.bus.NearStationResponse
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class NearStationMapViewModel @Inject constructor(
    private val nearStationListUseCase: NearStationListUseCase,
    locationUseCase: LocationUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ApiResult<List<NearStationResponse>>> = locationUseCase().map {
        it.successOrNull() ?: GPSModel()
    }.flatMapLatest {
        nearStationListUseCase(it.longitude.toString(), it.latitude.toString())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ApiResult.Loading
    )

    val stationFlow: StateFlow<List<NearStationResponse>> = uiState.map {
        it.successOrNull() ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )
}