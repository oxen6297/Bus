package sb.park.bus.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sb.park.bus.presentation.utils.KeyFile
import sb.park.domain.usecases.StationInfoUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.StationInfoResponse
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class StationMapViewModel @Inject constructor(
    stationInfoUseCase: StationInfoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _argData = savedStateHandle.getLiveData<BusStationResponse>(KeyFile.MAP_ARG_KEY)
    val argData: LiveData<BusStationResponse>
        get() = _argData

    val uiState: StateFlow<ApiResult<List<StationInfoResponse>>> =
        stationInfoUseCase(argData.value!!.arsId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ApiResult.Loading
        )

    val infoFlow: StateFlow<List<StationInfoResponse>> = uiState.map {
        it.successOrNull() ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )
}