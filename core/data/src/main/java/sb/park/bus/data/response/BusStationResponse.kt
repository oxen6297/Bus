package sb.park.bus.data.response

data class BusStationResponse(
    val busRouteNm: String,
    val seq: String,
    val stationId: String,
    val stationNm: String,
    val gpsX: String,
    val gpsY: String,
    val direction: String,
    val beginTm: String,
    val lastTm: String
)