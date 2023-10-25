package sb.park.bus.feature.main.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sb.park.bus.domain.usecase.BusIdUseCase
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(private val busIdUseCase: BusIdUseCase) : ViewModel() {
    
}