package sb.park.bus.data.call

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sb.park.model.ApiResult
import java.lang.reflect.Type

class ApiResultCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<ApiResult<R>> {

    override fun enqueue(callback: Callback<ApiResult<R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@ApiResultCall, Response.success(response.toApiResult()))
            }

            private fun Response<R>.toApiResult(): ApiResult<R> {
                body()?.let { body -> return ApiResult.Success(body) }

                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    ApiResult.Success(Unit as R)
                } else {
                    ApiResult.Error(
                        IllegalStateException(
                            "body is empty"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<R?>, throwable: Throwable) {
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(ApiResult.Error(throwable))
                )
            }
        }
    )

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ApiResultCall(delegate.clone(), successType)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResult<R>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}