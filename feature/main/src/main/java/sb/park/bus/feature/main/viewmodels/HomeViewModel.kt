package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.domain.usecases.BitCoinUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.model.ApiResult
import sb.park.model.response.FavoriteEntity
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    bitCoinUseCase: BitCoinUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    val uiState = bitCoinUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ApiResult.Loading
    )

    val bitCoinFlow = uiState.map { it.successOrNull()?.data }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    val favoriteFlow = favoriteUseCase.getFavorite().map { it.successOrNull() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    fun deleteAll() {
        viewModelScope.launch {
            favoriteUseCase.deleteAll()
        }
    }
}