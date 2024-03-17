package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
import sb.park.model.throwableOrNull
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

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asLiveData()

    private val _locationFlow =
        MutableStateFlow<ApiResult<List<BusLocationResponse>>>(ApiResult.Loading)
    val locationFlow = _locationFlow.asStateFlow()

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

    init {
        viewModelScope.launch {
            favoriteUseCase.getFavorite().collectLatest {
                it.successOrNull()?.forEach { favoriteList ->
                    if (favoriteList.busId == bus.value?.busId) {
                        _isFavorite.emit(true)
                    }
                }
                it.throwableOrNull()?.let { error ->
                    _errorFlow.emit(error)
                }
            }
        }
    }

    fun deleteFavorite() {
        viewModelScope.launch {
            runCatching {
                favoriteUseCase.deleteFavorite(bus.value?.busId!!)
            }.onSuccess {
                _isFavorite.emit(false)
            }.onFailure {
                _errorFlow.emit(it)
            }
        }
    }

    fun addFavorite(showToast: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                favoriteUseCase.insertFavorite(
                    FavoriteEntity(
                        busNumber = bus.value?.busRouteNm!!,
                        busId = bus.value?.busId!!,
                        startDirection = bus.value?.startDirection!!,
                        endDirection = bus.value?.endDirection!!,
                        busType = bus.value?.routeType!!
                    )
                )
            }.onSuccess {
                _isFavorite.emit(true)
                showToast()
            }.onFailure {
                _errorFlow.emit(it)
            }
        }
    }

    fun setLeftBtnText(): String = "${bus.value?.startDirection!!} 방향"

    fun setRightBtnText(): String = "${bus.value?.endDirection!!} 방향"
}