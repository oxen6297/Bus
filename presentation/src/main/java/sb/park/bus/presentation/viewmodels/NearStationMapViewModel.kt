package sb.park.bus.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sb.park.domain.usecases.NearStationListUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.NearStationResponse
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class NearStationMapViewModel @Inject constructor(nearStationListUseCase: NearStationListUseCase) :
    ViewModel() {

    val uiState: StateFlow<ApiResult<List<NearStationResponse>>> = nearStationListUseCase().stateIn(
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