package sb.park.bus.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import sb.park.model.response.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}