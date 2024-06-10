package sb.park.bus.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.domain.usecases.FileUseCase
import sb.park.domain.usecases.InsertBitCoinUseCase
import sb.park.model.successOrNull
import sb.park.model.throwableOrNull
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val insertBitCoinUseCase: InsertBitCoinUseCase,
    fileUseCase: FileUseCase
) : ViewModel() {

    val fileFlow: StateFlow<Int> = fileUseCase().map {
        it.successOrNull() ?: 0
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = 0
    )

    val errorFlow: StateFlow<Throwable?> = fileUseCase().map {
        it.throwableOrNull()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = null
    )

    fun insertBitCoin() {
        viewModelScope.launch {
            insertBitCoinUseCase()
        }
    }
}