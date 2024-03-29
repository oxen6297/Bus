package sb.park.model.response.bus

import java.io.Serializable

data class DeliveryData(
    val busId: String,
    val busRouteNm: String,
    val startDirection: String,
    val endDirection: String,
    val routeType: String,
    val stationId: String? = null,
    val stationName: String? = null,
    val type: Int = Type.BUS.type
) : Serializable {

    fun toFavorite(
        type: Int = Type.BUS.type,
        stationId: String? = null,
        stationName: String? = null
    ): FavoriteEntity = FavoriteEntity(
        busNumber = busRouteNm,
        busId = busId,
        startDirection = startDirection,
        endDirection = endDirection,
        busType = routeType,
        station = stationId,
        stationName = stationName,
        type = type
    )

    enum class Type(val type: Int) {
        BUS(0), STATION(1)
    }
}
