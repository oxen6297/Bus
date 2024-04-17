package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.domain.usecases.BitCoinUseCase
import sb.park.domain.usecases.ChartUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.domain.usecases.GetExpandedUseCase
import sb.park.domain.usecases.SaveExpandedUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bitcoin.BaseResponse
import sb.park.model.response.bitcoin.BitCoinResponse
import sb.park.model.response.bus.FavoriteEntity
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    bitCoinUseCase: BitCoinUseCase,
    chartUserCase: ChartUseCase,
    getExpandedUseCase: GetExpandedUseCase,
    private val saveExpandedUseCase: SaveExpandedUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    val uiState: StateFlow<ApiResult<BaseResponse>> = bitCoinUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ApiResult.Loading
    )

    val bitCoinFlow: StateFlow<BitCoinResponse?> = uiState.map {
        it.successOrNull()?.data
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = null
    )

    val chartFlow: StateFlow<List<CandleEntry>> = chartUserCase().map {
        it.successOrNull() ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    val isExpanded: StateFlow<Boolean> = getExpandedUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = false
    )

    private val _favoriteFlow = MutableStateFlow<List<FavoriteEntity>>(emptyList())
    val favoriteFlow = _favoriteFlow.asStateFlow()

    fun deleteAll() {
        viewModelScope.launch {
            favoriteUseCase.deleteAll {
                _favoriteFlow.emit(emptyList())
            }
        }
    }

    fun getFavorite() {
        viewModelScope.launch {
            _favoriteFlow.emit(favoriteUseCase.getFavorite())
        }
    }

    fun clickExpand() {
        viewModelScope.launch {
            saveExpandedUseCase(!isExpanded.value)
        }
    }
}