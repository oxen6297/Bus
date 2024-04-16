package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class BusStationResponse(
    @SerializedName("busRouteNm") val busRouteNm: String,
    @SerializedName("seq") val seq: String,
    @SerializedName("station") val stationId: String,
    @SerializedName("stationNm") val stationNm: String,
    @SerializedName("gpsX") val gpsX: String,

    @SerializedName("gpsY") val gpsY: String,
    @SerializedName("direction") val direction: String,
    @SerializedName("beginTm") val beginTm: String,
    @SerializedName("lastTm") val lastTm: String,
    @SerializedName("routeType") val routeType: String,

    @SerializedName("section") val sectionId: String,
    @SerializedName("transYn") val isTransfer: String,

    var busPosition: String?,
    var isRun: String?,
    var isLast: String?,
    var congestion: String?,
    var nextStId: String?,
    var isHere: Boolean = false,
) {
    var onFavorite: () -> Unit = {}
    var isFavorite: Boolean = false
}