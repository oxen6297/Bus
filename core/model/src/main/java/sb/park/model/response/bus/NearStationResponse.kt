package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class NearStationResponse(
    @SerializedName("stationId") val stationId: String,
    @SerializedName("stationNm") val stationNm: String,
    @SerializedName("gpsX") val gpsX: String,
    @SerializedName("gpsY") val gpsY: String,
    @SerializedName("arsId") val arsId: String
)
