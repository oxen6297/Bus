package sb.park.bus.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import sb.park.model.response.bitcoin.BitCoinEntity
import sb.park.model.response.bus.FavoriteEntity

@Database(entities = [BitCoinEntity::class], version = 1)
abstract class BitCoinDatabase : RoomDatabase() {

    abstract fun bitCoinDao(): BitCoinDao
}