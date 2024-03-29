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

    val uiState = busStationUseCase(bus.value!!).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ApiResult.Loading
    )

    val stationFlow = uiState.map { it.successOrNull() }.stateIn(
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

    fun addFavorite() {
        viewModelScope.launch {
            favoriteUseCase.insertFavorite(bus.value!!.toFavorite(FavoriteEntity.Type.BUS.type))
            _isFavorite.value = true
        }
    }

    fun getTransferPosition(): Int = stationFlow.value?.indexOfFirst {
        it.isTransfer == "Y"
    } ?: ((stationFlow.value?.size ?: 0) / 2)
}