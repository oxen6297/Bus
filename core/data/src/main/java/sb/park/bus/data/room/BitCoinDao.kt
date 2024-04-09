package sb.park.bus.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import sb.park.model.response.bitcoin.BitCoinEntity

@Dao
interface BitCoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(bitCoinEntity: BitCoinEntity)

    @Query("SELECT * FROM BitCoinEntity ORDER BY date")
    suspend fun getData(): List<BitCoinEntity>

    @Query("DELETE FROM BitCoinEntity WHERE date = (SELECT MIN(date) FROM BitCoinEntity)")
    suspend fun deleteOldData()

    @Transaction
    suspend fun insertNewData(bitCoinData: BitCoinEntity) {
        if (getData().size >= 100) {
            deleteOldData()
        }

        insertData(bitCoinData)
    }
}