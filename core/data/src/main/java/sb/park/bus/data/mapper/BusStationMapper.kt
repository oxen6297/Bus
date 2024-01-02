package sb.park.bus.data.mapper

import sb.park.bus.data.BusType
import sb.park.bus.data.response.BusStationResponse

internal fun BusStationResponse.toData(): BusStationResponse = BusStationResponse(
    busRouteNm = busRouteNm,
    seq = seq,
    stationId = stationId,
    stationNm = stationNm,
    gpsX = gpsX,
    gpsY = gpsY,
    direction = direction,
    beginTm = beginTm,
    lastTm = lastTm,
    routeType = BusType.values().find {
        it.type == routeType
    }?.typeName ?: ""
)