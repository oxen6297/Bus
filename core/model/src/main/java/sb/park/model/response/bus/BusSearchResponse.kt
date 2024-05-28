package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class BusSearchResponse(
    @SerializedName("busId") val busId: String,
    @SerializedName("busRouteNm") val busRouteNm: String,
    @SerializedName("startDirection") val startDirection: String,
    @SerializedName("endDirection") val endDirection: String,
    @SerializedName("routeType") val routeType: String,

    val stationId: String? = null,
    val stationName: String? = null,
    val type: Int = ArgumentData.Type.BUS.type
) {
    fun toArgument(): ArgumentData = ArgumentData(
        busId = this.busId,
        busRouteNm = this.busRouteNm,
        startDirection = this.startDirection,
        rawStartDirection = this.startDirection,
        endDirection = this.endDirection,
        rawEndDirection = this.endDirection,
        routeType = this.routeType,
        stationId = this.stationId,
        stationName = this.stationName,
        type = this.type
    )
}