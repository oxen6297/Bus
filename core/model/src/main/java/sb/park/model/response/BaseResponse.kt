package sb.park.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: BitCoinResponse
)
