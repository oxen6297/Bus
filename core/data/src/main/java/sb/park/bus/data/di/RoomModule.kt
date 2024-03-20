package sb.park.bus.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sb.park.bus.data.room.FavoriteDao
import sb.park.bus.data.room.FavoriteDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {
    @Singleton
    @Provides
    fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoriteDatabase =
        Room.databaseBuilder(context, FavoriteDatabase::class.java, "bus-favorite").addMigrations(
            MIGRATION_1_TO_2, MIGRATION_2_TO_3
        ).build()

    @Singleton
    @Provides
    fun provideFavoriteDao(db: FavoriteDatabase): FavoriteDao = db.favoriteDao()

    private val MIGRATION_1_TO_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.apply {
                execSQL("ALTER TABLE FavoriteEntity ADD COLUMN startDirection TEXT NOT NULL DEFAULT ''")
                execSQL("ALTER TABLE FavoriteEntity ADD COLUMN endDirection TEXT NOT NULL DEFAULT ''")
                execSQL("ALTER TABLE FavoriteEntity ADD COLUMN busType TEXT NOT NULL DEFAULT ''")
            }
        }
    }

    private val MIGRATION_2_TO_3: Migration = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.apply {
                execSQL("ALTER TABLE FavoriteEntity ADD COLUMN stationId TEXT")
                execSQL("ALTER TABLE FavoriteEntity ADD COLUMN type TEXT NOT NULL DEFAULT 'bus'")
            }
        }
    }
}