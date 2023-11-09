package sb.park.bus.data.response

import com.google.gson.annotations.SerializedName

data class BusIdResponse(
    val routeId: Long,
    val routeName: String,
    @SerializedName("Num") val num: Int,
    val nodeId: Long,
    val arsId: String,
    val stopName: String,
    val latitude: Double,
    val longitude: Double
)
