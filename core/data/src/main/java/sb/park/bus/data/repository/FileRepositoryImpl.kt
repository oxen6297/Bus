package sb.park.bus.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import sb.park.bus.data.AppDispatchers
import sb.park.bus.data.Dispatcher
import sb.park.bus.data.service.BusIdService
import sb.park.model.ApiResult
import sb.park.model.response.bus.BusIdResponse
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

internal class FileRepositoryImpl @Inject constructor(
    private val busIdService: BusIdService,
    private val file: File,
    @Dispatcher(AppDispatchers.IO) private val coroutineDispatcher: CoroutineDispatcher
) : FileRepository {

    override fun downloadFile(): Flow<ApiResult<Int>> = channelFlow<ApiResult<Int>> {
        val response = busIdService.getBusId()

        if (response.version == loadFile()?.version) {
            send(ApiResult.Success(100))
            return@channelFlow
        } else {
            file.delete()
        }

        FileOutputStream(file).use {
            val responseByte = Gson().toJson(response).toByteArray()
            val totalSize = responseByte.size
            var progressSize = 0
            while (progressSize < totalSize) {
                val bytesToWrite = minOf(CHUNK_SIZE, totalSize - progressSize)
                it.run {
                    write(responseByte, progressSize, bytesToWrite)
                    flush()
                }
                progressSize += bytesToWrite
                send(ApiResult.Success((progressSize.toFloat() / totalSize * 100).toInt()))
                delay(100)
            }
        }
    }.onStart {
        emit(ApiResult.Loading)
    }.catch {
        emit(ApiResult.Error(it))
    }.flowOn(coroutineDispatcher)

    private fun loadFile(): BusIdResponse? {
        return if (file.exists()) {
            Gson().fromJson(
                String(file.readBytes()),
                object : TypeToken<BusIdResponse>() {}.type
            )
        } else null
    }

    companion object {
        private const val CHUNK_SIZE = 1024
    }
}