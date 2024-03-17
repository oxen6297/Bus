package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class BusLocationResponse(
    @SerializedName("sectOrd") val sectOrd: String,
    @SerializedName("sectionId") val sectionId: String,
    @SerializedName("dataTm") val dataTm: String,
    @SerializedName("gpsX") val gpsX: String,
    @SerializedName("gpsY") val gpsY: String,

    @SerializedName("vehId") val vehId: String,
    @SerializedName("plainNo") val plainNo: String,
    @SerializedName("nextStTm") val nextStTm: String,
    @SerializedName("isrunyn") val isrunyn: String,
    @SerializedName("islastyn") val islastyn: String,

    @SerializedName("congetion") val congetion: String,
    @SerializedName("nextStId") val nextStId: String,
)
