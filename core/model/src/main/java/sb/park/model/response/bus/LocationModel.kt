package sb.park.model.response.bus

data class LocationModel(
    var position: Int? = null,
    var arriveTime: String? = null,
    var distance: Int = Int.MAX_VALUE
)
