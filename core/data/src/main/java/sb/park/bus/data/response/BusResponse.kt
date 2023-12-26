package sb.park.bus.data.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class BusResponse(
    @SerializedName("msgBody") val msgBody: Data
) {
    data class Data(
        @SerializedName("itemList") val itemList: JsonElement
    )
}
