package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class BusArriveResponse(
    @SerializedName("arrmsg1") val arriveTime: String
)
