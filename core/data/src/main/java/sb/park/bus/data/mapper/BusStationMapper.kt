package sb.park.bus.data.mapper

import sb.park.model.BusType
import sb.park.model.response.bus.BusStationResponse

internal fun BusStationResponse.toData(
    isFavorite: Boolean = false,
    isHere: Boolean = false,
    onFavorite: () -> Unit = {}
): BusStationResponse = BusStationResponse(
    busRouteNm = this.busRouteNm,
    seq = this.seq,
    stationId = this.stationId,
    stationNm = this.stationNm,
    gpsX = this.gpsX,
    gpsY = this.gpsY,
    direction = this.direction,
    beginTm = this.beginTm,
    lastTm = this.lastTm,
    section = this.section,
    routeType = BusType.entries.find {
        this.routeType == it.type
    }?.typeName ?: this.routeType,
    isTransfer = this.isTransfer,
    isHere = isHere
).apply {
    this.isFavorite = isFavorite
    this.onFavorite = onFavorite
}