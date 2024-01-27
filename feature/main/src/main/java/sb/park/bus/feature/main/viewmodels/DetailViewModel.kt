package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sb.park.domain.usecases.BusStationUseCase
import sb.park.model.ApiResult
import sb.park.model.successOrNull
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    busStationUseCase: BusStationUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val busId by lazy {
        savedStateHandle.get<String>("busId") ?: throw NullPointerException()
    }

    val uiState = busStationUseCase(busId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ApiResult.Loading
    )

    val stationFlow = uiState.map { it.successOrNull() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
}