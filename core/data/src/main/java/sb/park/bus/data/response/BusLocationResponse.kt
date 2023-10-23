package sb.park.bus.data.response

data class BusLocationResponse(
    val sectOrd: String,
    val sectionId: String,
    val dataTm: String,
    val gpsX: String,
    val gpsY: String,
    val vehId: String,
    val plainNo: String,
    val nextStTm: String,
    val isrunyn: String,
    val islastyn: String,
    val congetion: String,
    val nextStId: String,
)
