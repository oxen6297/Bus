package sb.park.bus.data.response

import com.google.gson.annotations.SerializedName

data class BusStationResponse(
    @SerializedName("busRouteNm") val busRouteNm: String,
    @SerializedName("seq") val seq: String,
    @SerializedName("stationId") val stationId: String?,
    @SerializedName("stationNm") val stationNm: String,
    @SerializedName("gpsX") val gpsX: String,

    @SerializedName("gpsY") val gpsY: String,
    @SerializedName("direction") val direction: String,
    @SerializedName("beginTm") val beginTm: String,
    @SerializedName("lastTm") val lastTm: String,
    @SerializedName("routeType") val routeType: String
)