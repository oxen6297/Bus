package sb.park.model.response.bus

import java.io.Serializable

data class ArgumentData(
    val busId: String,
    val busRouteNm: String,
    val startDirection: String,
    val rawStartDirection: String,
    val endDirection: String,

    val rawEndDirection: String,
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
        busNumber = this.busRouteNm,
        busId = this.busId,
        startDirection = this.rawStartDirection,
        endDirection = this.rawEndDirection,
        busType = this.routeType,
        station = stationId,
        stationName = stationName,
        type = type
    )

    enum class Type(val type: Int) {
        BUS(0), STATION(1)
    }
}
