package sb.park.bus.data.repository

import kotlinx.coroutines.flow.Flow
import sb.park.model.ApiResult

interface FileRepository {

    fun downloadFile(): Flow<ApiResult<Int>>
}