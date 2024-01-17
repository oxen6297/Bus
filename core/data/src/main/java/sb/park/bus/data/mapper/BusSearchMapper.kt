package sb.park.bus.data.mapper

import sb.park.bus.data.response.BusSearchResponse
import sb.park.bus.data.response.BusStationResponse

internal fun List<BusStationResponse>.toSearch(busId: String): List<BusSearchResponse> {
    return listOf(
        BusSearchResponse(
            busId = busId,
            busRouteNm = "${get(0).busRouteNm}번",
            startDirection = get(0).direction,
            endDirection = get(lastIndex).direction,
            routeType = get(lastIndex).routeType
        )
    )
}