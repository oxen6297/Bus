package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sb.park.bus.data.model.BaseResponse
import sb.park.bus.domain.usecase.BitCoinUseCase
import sb.park.bus.feature.main.theme.UiState
import javax.inject.Inject

@HiltViewModel
class BitCoinViewModel @Inject constructor(private val bitCoinUseCase: BitCoinUseCase) :
    ViewModel() {

    private val _bitCoinFlow = MutableStateFlow<UiState<BaseResponse>>(UiState.Loading)
    val bitCoinFlow = _bitCoinFlow.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _bitCoinFlow.emit(UiState.Success(bitCoinUseCase()))
            } catch (e: Exception) {
                _bitCoinFlow.emit(UiState.Error(e))
            }
        }
    }
}