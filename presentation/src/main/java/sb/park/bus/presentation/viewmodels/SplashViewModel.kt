package sb.park.bus.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sb.park.domain.usecases.InsertBitCoinUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val insertBitCoinUseCase: InsertBitCoinUseCase
) : ViewModel() {

    fun insertBitCoin() {
        viewModelScope.launch {
            insertBitCoinUseCase()
        }
    }
}