package sb.park.model.response.bitcoin

import com.google.gson.annotations.SerializedName

data class BitCoinResponse(
    @SerializedName("fluctate_rate_24H") val changeRatio: String,
    @SerializedName("max_price") val maxPrice: String,
    @SerializedName("min_price") val minPrice: String,
    @SerializedName("date") val date: String
)
