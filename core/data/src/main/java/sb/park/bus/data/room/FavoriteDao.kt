package sb.park.bus.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity ORDER BY id")
    suspend fun getFavorite(): List<FavoriteEntity>

    @Query("DELETE FROM FavoriteEntity WHERE id = :id")
    suspend fun deleteFavorite(id: Int)

    @Query("DELETE FROM FavoriteEntity")
    suspend fun deleteAll()
}