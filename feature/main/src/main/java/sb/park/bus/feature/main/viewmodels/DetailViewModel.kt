package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.utils.KeyFile
import sb.park.domain.usecases.BusLocationUseCase
import sb.park.domain.usecases.BusStationUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusLocationResponse
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.FavoriteEntity
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    busStationUseCase: BusStationUseCase,
    private val busLocationUseCase: BusLocationUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bus = savedStateHandle.getLiveData<BusSearchResponse>(KeyFile.BUS_KEY)
    val bus: LiveData<BusSearchResponse>
        get() = _bus

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _locationFlow =
        MutableStateFlow<ApiResult<List<BusLocationResponse>>>(ApiResult.Loading)
    val locationFlow = _locationFlow.asStateFlow()

    val uiState = busStationUseCase(bus.value?.busId!!).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ApiResult.Loading
    )

    val stationFlow = uiState.map {
        it.successOrNull()?.map { response ->
            response.apply {
                setStationFavorite(this)
                onFavorite = {
                    onFavorite(this)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            favoriteUseCase.getFavorite().forEach { favoriteList ->
                if (favoriteList.busId == bus.value?.busId) {
                    _isFavorite.value = true
                }
            }
        }
    }

    fun deleteFavorite() {
        viewModelScope.launch {
            favoriteUseCase.deleteFavorite(bus.value?.busId!!)
            _isFavorite.value = false
        }
    }

    fun addFavorite(type: Int, stationId: String? = null, stationName: String? = null) {
        viewModelScope.launch {
            favoriteUseCase.insertFavorite(
                FavoriteEntity(
                    busNumber = bus.value?.busRouteNm!!,
                    busId = bus.value?.busId!!,
                    startDirection = bus.value?.startDirection!!,
                    endDirection = bus.value?.endDirection!!,
                    busType = bus.value?.routeType!!,
                    station = stationId,
                    stationName = stationName,
                    type = type
                )
            )
            if (type == FavoriteEntity.Type.BUS.type) {
                _isFavorite.value = true
            }
        }
    }

    private fun onFavorite(response: BusStationResponse) {
        viewModelScope.launch {
            if (favoriteUseCase.getStationFavorite(response.stationId)) {
                favoriteUseCase.deleteStationFavorite(response.stationId)
            } else {
                addFavorite(
                    FavoriteEntity.Type.STATION.type,
                    response.stationId,
                    response.stationNm
                )
            }
        }
    }

    private fun setStationFavorite(response: BusStationResponse) {
        viewModelScope.launch {
            response.isFavorite = favoriteUseCase.getStationFavorite(response.stationId)
        }
    }

    fun getTransferPosition(): Int = stationFlow.value?.indexOfFirst {
        it.isTransfer == "Y"
    } ?: ((stationFlow.value?.size ?: 0) / 2)
}