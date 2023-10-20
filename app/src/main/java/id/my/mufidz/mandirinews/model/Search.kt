package id.my.mufidz.mandirinews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Search(
    @PrimaryKey
    val query: String = "",
    @ColumnInfo("created_at")
    val createdAt: Date = Date()
)
