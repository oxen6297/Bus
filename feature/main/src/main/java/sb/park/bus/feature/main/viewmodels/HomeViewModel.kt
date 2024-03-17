package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sb.park.domain.usecases.BitCoinUseCase
import sb.park.domain.usecases.FavoriteUseCase
import sb.park.model.ApiResult
import sb.park.model.response.bus.FavoriteEntity
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

    private val _favoriteFlow = MutableStateFlow<List<FavoriteEntity>?>(null)
    val favoriteFlow = _favoriteFlow.asStateFlow()

    private val _clickable = MutableLiveData(false)
    val clickable: LiveData<Boolean>
        get() = _clickable

    init {
        viewModelScope.launch {
            favoriteFlow.collectLatest {
                _clickable.value = !it.isNullOrEmpty()
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            favoriteUseCase.deleteAll()
            getFavorite()
        }
    }

    fun getFavorite(){
        viewModelScope.launch {
            favoriteUseCase.getFavorite().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = null
            ).collectLatest {
                _favoriteFlow.emit(it?.successOrNull())
            }
        }
    }
}