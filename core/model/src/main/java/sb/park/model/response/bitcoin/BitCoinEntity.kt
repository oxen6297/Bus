package sb.park.model.response.bitcoin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BitCoinEntity(
    @PrimaryKey
    @ColumnInfo val date: String,
    @ColumnInfo val changeRatio: String,
    @ColumnInfo val openPrice: String,
    @ColumnInfo val closePrice: String,
    @ColumnInfo val maxPrice: String,
    @ColumnInfo val minPrice: String
)
