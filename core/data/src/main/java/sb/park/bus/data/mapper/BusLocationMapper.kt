package sb.park.bus.data.mapper

import sb.park.bus.data.response.BusLocationResponse

internal fun BusLocationResponse.toData(): BusLocationResponse = BusLocationResponse(
    sectOrd = this.sectOrd,
    sectionId = this.sectionId,
    dataTm = this.dataTm,
    gpsX = this.gpsX,
    gpsY = this.gpsY,
    vehId = this.vehId,
    plainNo = this.plainNo,
    nextStTm = if (this.nextStTm.toInt() < 60) {
        this.nextStTm
    } else {
        "${this.nextStTm.toInt() / 60}분${this.nextStTm.toInt() % 60}초"
    },
    isrunyn = if (this.isrunyn == "1") "운행" else "운행 종료",
    islastyn = if (this.islastyn == "1") "막차" else "막차 아님",
    congetion = when (this.congetion) {
        "0" -> "없음"
        "3" -> "여유"
        "4" -> "보통"
        "5" -> "혼잡"
        else -> "매우혼잡"
    },
    nextStId = this.nextStId
)

