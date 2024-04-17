package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    val isExpanded: Flow<Boolean>

    suspend fun saveIsExpanded(isExpanded: Boolean)
}