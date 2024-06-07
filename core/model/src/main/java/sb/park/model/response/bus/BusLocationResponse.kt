package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class BusLocationResponse(
    @SerializedName("sectionId") val sectionId: String,
    @SerializedName("isrunyn") val isrunyn: String,
    @SerializedName("islastyn") val islastyn: String,
    @SerializedName("congetion") val congetion: String,
    @SerializedName("nextStId") val nextStId: String,
)
