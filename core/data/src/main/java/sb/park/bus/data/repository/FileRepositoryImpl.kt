package sb.park.bus.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.service.BusIdService
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusIdResponse
import sb.park.model.safeFlow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

internal class FileRepositoryImpl @Inject constructor(
    private val busIdService: BusIdService,
    private val file: File,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : FileRepository {

    override fun downloadFile(): Flow<ApiResult<Float>> = safeFlow {

        val response = busIdService.getBusId()
        val responseByte = Gson().toJson(response).toByteArray()

        if (file.exists()) {
            val loadFile = Gson().fromJson<BusIdResponse>(
                String(file.readBytes()),
                object : TypeToken<BusIdResponse>() {}.type
            )
            if (response.version == loadFile.version) {
                return@safeFlow 100f
            }
        }

        FileOutputStream(file).use {
            val totalSize = responseByte.size
            val chunkSize = 1024
            var progressSize = 0

            while (progressSize < totalSize) {
                val bytesToWrite = minOf(chunkSize, totalSize - progressSize)
                it.write(responseByte, progressSize, bytesToWrite)
                progressSize += bytesToWrite
            }
            progressSize.toFloat() / totalSize * 100
        }
    }.flowOn(coroutineDispatcher)
}