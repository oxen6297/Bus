package sb.park.bus.data.mapper

import sb.park.model.response.BusSearchResponse
import sb.park.model.response.BusStationResponse

internal fun List<BusStationResponse>.toSearch(busId: String): Set<BusSearchResponse> {
    return setOf(
        BusSearchResponse(
            busId = busId,
            busRouteNm = "${first().busRouteNm}번",
            startDirection = first().direction,
            endDirection = get(lastIndex).direction,
            routeType = get(lastIndex).routeType
        )
    )
}