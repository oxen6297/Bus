package sb.park.bus.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : DataStoreRepository {

    override val isExpanded: Flow<Boolean> = datastore.data.map {
        it[PrefKey.IS_EXPANDED] ?: false
    }

    override suspend fun saveIsExpanded(isExpanded: Boolean) {
        datastore.edit {
            it[PrefKey.IS_EXPANDED] = isExpanded
        }
    }

    object PrefKey {
        val IS_EXPANDED = booleanPreferencesKey("IS_EXPANDED")
    }
}