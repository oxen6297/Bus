package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class BusIdResponse(
    @SerializedName("routeId") val routeId: Long,
    @SerializedName("routeName") val routeName: String
)