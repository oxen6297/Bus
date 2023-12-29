package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.data.ApiResult
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.bus.data.response.BaseResponse
import javax.inject.Inject

@HiltViewModel
class BitCoinViewModel @Inject constructor(private val bitCoinRepository: BitCoinRepository) :
    ViewModel() {

    private val _bitCoinFlow = MutableStateFlow<ApiResult<BaseResponse>>(ApiResult.Loading)
    val bitCoinFlow = _bitCoinFlow.asStateFlow()

    fun getCoinData() {
        viewModelScope.launch {
            bitCoinRepository.getData().collectLatest {
                _bitCoinFlow.emit(it)
            }
        }
    }
}