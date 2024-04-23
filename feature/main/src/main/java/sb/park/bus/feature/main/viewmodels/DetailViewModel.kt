package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.utils.KeyFile
import sb.park.domain.usecases.BusStationUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    busStationUseCase: BusStationUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _argData = savedStateHandle.getLiveData<ArgumentData>(KeyFile.NAV_ARG_KEY)
    val argData: LiveData<ArgumentData>
        get() = _argData

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    val uiState: StateFlow<ApiResult<List<BusStationResponse>>> =
        busStationUseCase(argData.value!!).stateIn(
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

    fun setFavorite() {
        viewModelScope.launch {
            favoriteUseCase.getFavorite().collectLatest { list ->
                _isFavorite.value = list.any {
                    it.busId == argData.value?.busId && it.type == ArgumentData.Type.BUS.type
                }
            }
        }
    }

    fun deleteFavorite() {
        viewModelScope.launch {
            favoriteUseCase.deleteFavorite(argData.value?.busId!!) {
                _isFavorite.value = it
            }
        }
    }

    fun addFavorite(toast: () -> Unit) {
        viewModelScope.launch {
            favoriteUseCase.insertFavorite(argData.value!!.toFavorite()) {
                _isFavorite.value = it
                toast()
            }
        }
    }

    fun getTransferPosition(): Int = stationFlow.value.indexOfFirst {
        it.isTransfer == "Y"
    }.plus(5)
}