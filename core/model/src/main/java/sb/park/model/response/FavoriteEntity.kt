package sb.park.model.response

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
    @ColumnInfo val busType: String
) : Serializable
