package sb.park.bus.data.response

data class BusSearchResponse(
    val busRouteNm: String,
    val startDirection: String,
    val endDirection: String
)
