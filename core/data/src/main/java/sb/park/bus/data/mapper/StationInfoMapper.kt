package sb.park.bus.data.mapper

import sb.park.model.BusType
import sb.park.model.response.bus.StationInfoResponse

internal fun StationInfoResponse.toData(): StationInfoResponse {
    val regex = Regex("(\\d+분).*?(\\[.*)")

    return StationInfoResponse(
        busId = this.busId,
        busRouteNm = "${this.busRouteNm}번",
        direction = "${this.direction} 방면",
        firstArrive = this.firstArrive.replace(regex, "$1$2"),
        secondArrive = this.secondArrive.replace(regex, "$1$2"),
        nextStation = "다음 정류장: ${this.nextStation}",
        routeType = BusType.entries.find {
            this.routeType == it.type
        }?.typeName ?: this.routeType
    )
}