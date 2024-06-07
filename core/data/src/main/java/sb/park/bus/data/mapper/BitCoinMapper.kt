package sb.park.bus.data.mapper

import android.annotation.SuppressLint
import sb.park.model.response.bitcoin.BaseResponse
import sb.park.model.response.bitcoin.BitCoinEntity
import sb.park.model.response.bitcoin.BitCoinResponse
import java.text.SimpleDateFormat
import java.util.Date

internal fun BaseResponse.toData(): BaseResponse = BaseResponse(
    status = this.status,
    data = this.data.toData()
)

@SuppressLint("SimpleDateFormat")
private fun BitCoinResponse.toData(): BitCoinResponse {
    val regex = "(\\d)(?=(\\d{3})+\$)".toRegex()

    return BitCoinResponse(
        changeRatio = "${this.changeRatio}%",
        maxPrice = "${this.maxPrice.replace(regex, "\$1,")} 원",
        minPrice = "${this.minPrice.replace(regex, "\$1,")} 원",
        openPrice = this.openPrice,
        closePrice = this.closePrice,
        date = "기준일: ${SimpleDateFormat("yyyy-MM-dd").format(Date(this.date.toLong()))}"
    )
}

internal fun BitCoinResponse.toEntity(): BitCoinEntity = BitCoinEntity(
    changeRatio = this.changeRatio,
    openPrice = this.openPrice,
    closePrice = this.closePrice,
    maxPrice = this.maxPrice,
    minPrice = this.minPrice,
    date = this.date
)
