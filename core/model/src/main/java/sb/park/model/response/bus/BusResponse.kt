package sb.park.model.response.bus

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class BusResponse(
    @SerializedName("msgBody") val msgBody: ItemList
) {
    data class ItemList(
        @SerializedName("itemList") val itemList: JsonElement
    )
}
