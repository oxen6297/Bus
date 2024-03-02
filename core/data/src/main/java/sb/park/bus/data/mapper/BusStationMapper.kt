package sb.park.bus.data.mapper

import sb.park.model.BusType
import sb.park.model.response.BusStationResponse

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
    section = section,
    routeType = BusType.entries.find {
        routeType == it.type
    }?.typeName ?: routeType
)