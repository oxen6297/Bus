package sb.park.bus.data.mapper

import sb.park.model.BusType
import sb.park.model.response.bus.BusLocationResponse
import sb.park.model.response.bus.BusStationResponse

internal fun BusStationResponse.toData(
    isFavorite: Boolean = false,
    locationList: List<BusLocationResponse>,
    onFavorite: () -> Unit = {}
): BusStationResponse {

    val locationResponse = locationList.find {
        it.sectionId == sectionId
    }

    return BusStationResponse(
        busRouteNm = this.busRouteNm,
        seq = this.seq,
        stationId = this.stationId,
        stationNm = this.stationNm,
        gpsX = this.gpsX,
        gpsY = this.gpsY,
        direction = this.direction,
        beginTm = this.beginTm,
        lastTm = this.lastTm,
        sectionId = this.sectionId,
        isTransfer = this.isTransfer,

        routeType = BusType.entries.find {
            this.routeType == it.type
        }?.typeName ?: this.routeType,

        isHere = locationList.any {
            it.sectionId == sectionId
        },

        busPosition = busPosition,
        isRun = locationResponse?.isrunyn ?: "",
        isLast = locationResponse?.islastyn ?: "",
        congestion = locationResponse?.congetion ?: "",
        nextStId = locationResponse?.nextStId ?: ""
    ).apply {
        this.isFavorite = isFavorite
        this.onFavorite = onFavorite
    }
}

