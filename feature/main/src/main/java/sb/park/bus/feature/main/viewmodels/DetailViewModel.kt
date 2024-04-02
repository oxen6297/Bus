package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.utils.KeyFile
import sb.park.domain.usecases.BusLocationUseCase
import sb.park.domain.usecases.BusStationUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.DeliveryData
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    busStationUseCase: BusStationUseCase,
    private val busLocationUseCase: BusLocationUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _data = savedStateHandle.getLiveData<DeliveryData>(KeyFile.NAV_ARG_KEY)
    val data: LiveData<DeliveryData>
        get() = _data

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    val uiState = busStationUseCase(data.value!!).stateIn(
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
                if (favoriteList.busId == data.value?.busId) {
                    _isFavorite.value = true
                }
            }
        }
    }

    fun deleteFavorite() {
        viewModelScope.launch {
            favoriteUseCase.deleteFavorite(data.value?.busId!!) {
                _isFavorite.value = it
            }
        }
    }

    fun addFavorite() {
        viewModelScope.launch {
            favoriteUseCase.insertFavorite(data.value!!.toFavorite()) {
                _isFavorite.value = it
            }
        }
    }

    fun getTransferPosition(): Int = stationFlow.value?.indexOfFirst {
        it.isTransfer == "Y"
    }?.plus(5) ?: 0
}