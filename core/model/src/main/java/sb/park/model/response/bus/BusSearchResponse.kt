package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BusSearchResponse(
    @SerializedName("busId") val busId: String,
    @SerializedName("busRouteNm") val busRouteNm: String,
    @SerializedName("startDirection") val startDirection: String,
    @SerializedName("endDirection") val endDirection: String,
    @SerializedName("routeType") val routeType: String,
    val stationId: String? = null,
    val type: Int = FavoriteEntity.Type.BUS.type
) : Serializable
