package sb.park.bus.data.mapper

import sb.park.model.response.BusSearchResponse
import sb.park.model.response.BusStationResponse

internal fun List<BusStationResponse>.toSearch(busId: String): Set<BusSearchResponse> {
    return setOf(
        BusSearchResponse(
            busId = busId,
            busRouteNm = "${get(0).busRouteNm}번",
            startDirection = get(0).direction,
            endDirection = get(lastIndex).direction,
            routeType = get(lastIndex).routeType
        )
    )
}