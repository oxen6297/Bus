package sb.park.bus.data.mapper

import android.annotation.SuppressLint
import sb.park.bus.data.model.BitCoinModel
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
internal fun BitCoinModel.toData(): BitCoinModel = BitCoinModel(
    status = this.status,
    data = this.data,
    date = SimpleDateFormat("yyyy-MM-dd").format(this.date)
)