package sb.park.model.response.bus

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id: Int = 0,
    @ColumnInfo val busNumber: String,
    @ColumnInfo val busId: String,
    @ColumnInfo val startDirection: String,
    @ColumnInfo val endDirection: String,

    @ColumnInfo val busType: String,
    @ColumnInfo val station: String? = null,
    @ColumnInfo val stationName: String? = null,
    @ColumnInfo val type: Int,
) {
    fun toDelivery(): DeliveryData = DeliveryData(
        busId = this.busId,
        busRouteNm = this.busNumber,
        startDirection = this.startDirection + "방향",
        rawStartDirection = this.startDirection,
        endDirection = this.endDirection + "방향",
        rawEndDirection = this.endDirection,
        routeType = this.busType,
        stationId = this.station,
        stationName = this.stationName,
        type = this.type
    )
}
