package sb.park.domain.usecases

import kotlinx.coroutines.flow.Flow
import sb.park.bus.data.repository.FileRepository
import sb.park.model.ApiResult
import javax.inject.Inject

class FileUseCase @Inject constructor(private val fileRepository: FileRepository) {

    operator fun invoke():Flow<ApiResult<Int>> = fileRepository.downloadFile()
}