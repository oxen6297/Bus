package sb.park.bus.data.model

data class BusIdModel(
    val routeId: Long,
    val routeName: String,
    val Num: Int,
    val nodeId: Long,
    val arsId: String,
    val stopName: String,
    val latitude: Double,
    val longitude: Double
)
