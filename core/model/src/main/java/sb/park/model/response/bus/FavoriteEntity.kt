package sb.park.model.response.bus

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id: Int = 0,
    @ColumnInfo val busNumber: String,
    @ColumnInfo val busId: String,
    @ColumnInfo val startDirection: String,
    @ColumnInfo val endDirection: String,
    @ColumnInfo val busType: String,
    @ColumnInfo val stationId: String? = null,
    @ColumnInfo val type: Int,
) : Serializable {
    fun toSearch(): BusSearchResponse = BusSearchResponse(
        busId = this.busId,
        busRouteNm = this.busNumber,
        startDirection = this.startDirection,
        endDirection = this.endDirection,
        routeType = this.busType,
        stationId = this.stationId,
        type = this.type
    )

    enum class Type(val type: Int) {
        BUS(0), STATION(1)
    }
}
