package id.my.mufidz.mandirinews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.my.mufidz.mandirinews.model.Search

@Database(
    version = 1, entities = [Search::class]
)
@TypeConverters(DateConverters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun searchDao() : SearchDao
}