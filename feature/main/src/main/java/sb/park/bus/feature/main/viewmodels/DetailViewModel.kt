package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.LiveData
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
import sb.park.domain.usecases.BusStationUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.model.ApiResult
import sb.park.model.response.BusSearchResponse
import sb.park.model.response.FavoriteEntity
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    busStationUseCase: BusStationUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bus = savedStateHandle.getLiveData<BusSearchResponse>("bus")
    val bus: LiveData<BusSearchResponse>
        get() = _bus

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteUseCase.getFavorite().forEach {
                if (it.busId == bus.value?.busId) {
                    _isFavorite.emit(true)
                } else {
                    _isFavorite.emit(false)
                }
            }
        }
    }

    val uiState = busStationUseCase(bus.value?.busId!!).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ApiResult.Loading
    )

    val stationFlow = uiState.map { it.successOrNull() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    fun deleteFavorite() {
        viewModelScope.launch {
            _isFavorite.emit(false)
            favoriteUseCase.deleteFavorite(bus.value?.busId!!)
        }
    }

    fun addFavorite() {
        viewModelScope.launch {
            _isFavorite.emit(true)
            favoriteUseCase.insertFavorite(
                FavoriteEntity(
                    busNumber = bus.value?.busRouteNm!!,
                    busId = bus.value?.busId!!
                )
            )
        }
    }
}