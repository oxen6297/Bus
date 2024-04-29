package sb.park.bus.data.mapper

import sb.park.model.BusType
import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse

internal fun List<BusStationResponse>.toData(busId: String): Set<BusSearchResponse> {
    return setOf(
        BusSearchResponse(
            busId = busId,
            busRouteNm = "${first().busRouteNm}번",
            startDirection = first().direction,
            endDirection = last().direction,
            routeType = BusType.entries.find {
                last().routeType == it.type
            }?.typeName ?: last().routeType
        )
    )
}