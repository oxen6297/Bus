package sb.park.bus.data.mapper

import sb.park.model.response.bus.BusLocationResponse

internal fun BusLocationResponse.toData(): BusLocationResponse = BusLocationResponse(
    sectionId = this.sectionId,
    isrunyn = if (this.isrunyn == "1") "운행" else "운행 종료",
    islastyn = if (this.islastyn == "1") "막차" else "막차 아님",
    congetion = when (this.congetion) {
        "0" -> "여유"
        "3" -> "여유"
        "4" -> "보통"
        "5" -> "혼잡"
        else -> "매우혼잡"
    },
    nextStId = this.nextStId
)

