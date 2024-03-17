package sb.park.bus.data.mapper

import sb.park.model.response.bus.BusSearchResponse
import sb.park.model.response.bus.BusStationResponse

internal fun List<BusStationResponse>.toSearch(busId: String): Set<BusSearchResponse> {
    return setOf(
        BusSearchResponse(
            busId = busId,
            busRouteNm = "${first().busRouteNm}번",
            startDirection = first().direction,
            endDirection = last().direction,
            routeType = last().routeType
        )
    )
}