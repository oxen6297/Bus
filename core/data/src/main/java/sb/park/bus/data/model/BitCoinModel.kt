package sb.park.bus.data.model

import com.google.gson.annotations.SerializedName

data class BitCoinModel(
    val status: String,
    val data: List<DataModel>,
    val date: String
    ) {
    data class DataModel(
        @SerializedName("units_traded_24H") val unitsTraded: String,
        @SerializedName("acc_trade_value_24H") val accTradeValue: String,
        @SerializedName("fluctate_24H") val changeRate: String,
        @SerializedName("fluctate_rate_24H") val changeRatio: String,
    )
}
