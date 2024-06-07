package sb.park.model.response.bus

import com.google.gson.annotations.SerializedName

data class BusIdResponse(
    @SerializedName("version") val version: Int,
    @SerializedName("body") val body: Body
) {
    data class Body(
        @SerializedName("itemList") val itemList: List<Item>
    )

    data class Item(
        @SerializedName("routeId") val routeId: Long,
        @SerializedName("routeName") val routeName: String
    )
}