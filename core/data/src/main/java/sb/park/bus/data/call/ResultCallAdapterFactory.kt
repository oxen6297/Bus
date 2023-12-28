package sb.park.bus.data.call

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import sb.park.bus.data.ApiResult
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) {
            "Return 타입은 ApiResult<Foo> 또는 ApiResult<out Foo>로 정의되어야 합니다."
        }

        val wrapperType = getParameterUpperBound(0, returnType)
        if (getRawType(wrapperType) != ApiResult::class.java) return null
        check(wrapperType is ParameterizedType) {
            "Return 타입은 ApiResult<ResponseBody>로 정의되어야 합니다."
        }

        return ApiResultCallAdapter<Any>(getParameterUpperBound(0, wrapperType))
    }
}