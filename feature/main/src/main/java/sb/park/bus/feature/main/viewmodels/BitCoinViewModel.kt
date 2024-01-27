package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sb.park.domain.usecases.BitCoinUseCase
import sb.park.model.ApiResult
import sb.park.model.successOrNull
import javax.inject.Inject

@HiltViewModel
class BitCoinViewModel @Inject constructor(bitCoinUseCase: BitCoinUseCase) :
    ViewModel() {

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
}