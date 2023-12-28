package sb.park.bus.data.call

import retrofit2.Call
import retrofit2.CallAdapter
import sb.park.bus.data.ApiResult
import java.lang.reflect.Type

internal class ApiResultCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<ApiResult<R>>> {
    override fun adapt(call: Call<R>): Call<ApiResult<R>> = ApiResultCall(call, successType)

    override fun responseType(): Type = successType
}