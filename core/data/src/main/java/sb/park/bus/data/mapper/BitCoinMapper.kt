package sb.park.bus.data.mapper

import android.annotation.SuppressLint
import sb.park.bus.data.model.BitCoinModel
import sb.park.bus.data.model.CoinBaseModel
import java.text.SimpleDateFormat
import java.util.Date

internal fun CoinBaseModel.toData(): CoinBaseModel = CoinBaseModel(
    status = this.status,
    data = this.data.toData()
)

@SuppressLint("SimpleDateFormat")
internal fun BitCoinModel.toData(): BitCoinModel {
    val regex = "(\\d)(?=(\\d{3})+\$)".toRegex()

    return BitCoinModel(
        changeRatio = "${this.changeRatio}%",
        maxPrice = "${this.maxPrice.replace(regex,"\$1,")} 원",
        minPrice = "${this.minPrice.replace(regex,"\$1,")} 원",
        date = SimpleDateFormat("yyyy-MM-dd").format(Date(this.date.toLong()))
    )
}