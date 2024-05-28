package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class StationInfoResponse(
    @SerializedName("busRouteId") val busId: String,
    @SerializedName("rtNm") val busRouteNm: String,
    @SerializedName("adirection") val direction: String,
    @SerializedName("arrmsg1") val firstArrive: String,
    @SerializedName("arrmsg2") val secondArrive: String,
    @SerializedName("nxtStn") val nextStation: String,
    @SerializedName("routeType") val routeType: String
)
