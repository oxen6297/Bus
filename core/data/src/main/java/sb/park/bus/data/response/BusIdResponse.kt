package sb.park.bus.data.response

import com.google.gson.annotations.SerializedName

data class BusIdResponse(
    @SerializedName("routeId") val routeId: Long,
    @SerializedName("routeName") val routeName: String,
    @SerializedName("Num") val num: Int,
    @SerializedName("nodeId") val nodeId: Long,
    @SerializedName("arsId") val arsId: String,

    @SerializedName("stopName") val stopName: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
