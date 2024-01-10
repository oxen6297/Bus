package sb.park.bus.data.response

import com.google.gson.annotations.SerializedName

data class BusSearchResponse(
    @SerializedName("busRouteNm")val busRouteNm: String,
    @SerializedName("startDirection")val startDirection: String,
    @SerializedName("endDirection")val endDirection: String,
    @SerializedName("routeType")val routeType: String
)
