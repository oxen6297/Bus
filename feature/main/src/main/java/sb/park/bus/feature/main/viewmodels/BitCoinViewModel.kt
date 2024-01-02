package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sb.park.bus.data.ApiResult
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.bus.data.successOrNull
import javax.inject.Inject

@HiltViewModel
class BitCoinViewModel @Inject constructor(bitCoinRepository: BitCoinRepository) :
    ViewModel() {

    val uiState = bitCoinRepository.getData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ApiResult.Loading
    )

    val bitCoinFlow = uiState.map { it.successOrNull()?.data }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )
}