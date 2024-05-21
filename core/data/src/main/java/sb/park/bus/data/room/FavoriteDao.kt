package sb.park.bus.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import sb.park.model.response.bus.ArgumentData
import sb.park.model.response.bus.BusStationResponse
import sb.park.model.response.bus.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity ORDER BY id")
    suspend fun getFavorite(): List<FavoriteEntity>

    @Query("DELETE FROM FavoriteEntity WHERE busId = :busId AND type = 0")
    suspend fun deleteBusFavorite(busId: String)

    @Query("DELETE FROM FavoriteEntity WHERE station = :station AND type = 1")
    suspend fun deleteStationFavorite(station: String)

    @Query("DELETE FROM FavoriteEntity")
    suspend fun deleteAll()

    @Transaction
    suspend fun addFavorite(response: BusStationResponse, argumentData: ArgumentData) {
        val isFavorite = getFavorite().any {
            it.station == response.stationId && it.busId == argumentData.busId
        }

        if (isFavorite) {
            deleteStationFavorite(response.stationId)
        } else {
            insertFavorite(
                argumentData.toFavorite(
                    ArgumentData.Type.STATION.type,
                    response.stationId,
                    response.stationNm
                )
            )
        }
    }
}