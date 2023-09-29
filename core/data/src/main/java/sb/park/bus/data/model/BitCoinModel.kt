package sb.park.bus.data.model

import com.google.gson.annotations.SerializedName

data class BitCoinModel(
    @SerializedName("fluctate_rate_24H") val changeRatio: String,
    @SerializedName("max_price") val maxPrice: String,
    @SerializedName("min_price") val minPrice: String,
    val date: String
)
