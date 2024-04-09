package sb.park.bus.data.mapper

import com.github.mikephil.charting.data.CandleEntry
import sb.park.model.response.bitcoin.BitCoinEntity


fun BitCoinEntity.toCandle(index: Int): CandleEntry = CandleEntry(
    index.toFloat(),
    maxPrice.toFloat(),
    minPrice.toFloat(),
    openPrice.toFloat(),
    closePrice.toFloat()
)
