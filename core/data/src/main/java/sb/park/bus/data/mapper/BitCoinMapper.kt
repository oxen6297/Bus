package sb.park.bus.data.mapper

import android.annotation.SuppressLint
import sb.park.model.response.BaseResponse
import sb.park.model.response.BitCoinResponse
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
        date = "기준일: ${SimpleDateFormat("yyyy-MM-dd").format(Date(this.date.toLong()))}"
    )
}