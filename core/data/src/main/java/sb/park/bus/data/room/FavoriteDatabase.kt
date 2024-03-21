package sb.park.bus.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import sb.park.model.response.bus.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 4)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}