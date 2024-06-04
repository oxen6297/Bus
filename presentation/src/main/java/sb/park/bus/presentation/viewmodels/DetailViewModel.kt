package sb.park.bus.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.bus.presentation.utils.KeyFile
import sb.park.domain.usecases.BusStationUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.domain.usecases.NearStationUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.LocationModel
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val busStationUseCase: BusStationUseCase,
    private val nearStationUseCase: NearStationUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _argData = savedStateHandle.getLiveData<ArgumentData>(KeyFile.DETAIL_ARG_KEY)
    val argData: LiveData<ArgumentData>
        get() = _argData

    private val _locationFlow = MutableSharedFlow<LocationModel>()
    val locationFlow = _locationFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<ApiResult<List<BusStationResponse>>>(ApiResult.Loading)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = _uiState.flatMapLatest { busStationUseCase(argData.value!!) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ApiResult.Loading
    )

    val stationFlow: StateFlow<List<BusStationResponse>> = uiState.map {
        it.successOrNull() ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    val isFavorite: StateFlow<Boolean> = favoriteUseCase.isBusFavorite(argData.value!!).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = false
    )

    fun clickFavorite() {
        viewModelScope.launch {
            favoriteUseCase.addFavorite(argData.value!!)
        }
    }

    fun clickRefresh() {
        viewModelScope.launch {
            busStationUseCase(argData.value!!).collectLatest {
                _uiState.emit(it)
            }
        }
    }

    fun getNearStation() {
        viewModelScope.launch {
            nearStationUseCase(argData.value!!).mapNotNull {
                it.successOrNull()
            }.collectLatest {
                _locationFlow.emit(it)
            }
        }
    }

    fun transferPosition(): Int = stationFlow.value.indexOfFirst {
        it.isTransfer == "Y"
    }
}