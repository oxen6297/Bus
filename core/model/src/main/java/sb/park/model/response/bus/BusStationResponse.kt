package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    @SerializedName("busPosition") var busPosition: String?,
    @SerializedName("isRun") var isRun: String?,
    @SerializedName("isLast") var isLast: String?,

    @SerializedName("congestion") var congestion: String?,
    @SerializedName("nextStId") var nextStId: String?,
    @SerializedName("isHere") var isHere: Boolean = false,
    @SerializedName("isLocated") var isLocated: Boolean = false,
    @SerializedName("isFavorite") var isFavorite: Boolean = false
) : Serializable {
    @Transient
    var onFavorite: () -> Unit = {}
}