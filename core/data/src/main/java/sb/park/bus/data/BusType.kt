package sb.park.bus.data

import androidx.annotation.ColorInt
import sb.park.bus.core.data.R

enum class BusType(val type: String, val typeName: String, @ColorInt val color: Int) {
    PORT("1", "공항", R.color.sky_blue),
    COUNTRY("2", "마을", R.color.yellow),
    GANSEON("3", "간선", R.color.blue),
    JISEON("4", "지선", R.color.green),
    CYCLE("5", "순환", R.color.red),
    WIDE("6", "광역", R.color.purple),
    INCHEON("7", "인천", R.color.mint),
    GYUNGGI("8", "경기", R.color.black),
    COMMON("0", "공용", R.color.dark_gray)
}