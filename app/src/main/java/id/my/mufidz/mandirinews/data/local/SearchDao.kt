package id.my.mufidz.mandirinews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import id.my.mufidz.mandirinews.model.Search

@Dao
interface SearchDao {
    @Upsert(Search::class)
    suspend fun upsertSearch(search: Search)

    @Query("SELECT * FROM search ORDER BY created_at DESC LIMIT :limit")
    fun getLastSearch(limit: Int = 5) : List<Search>

    @Query("SELECT * FROM search WHERE `query` LIKE :query ORDER BY created_at DESC LIMIT :limit")
    fun searchHistory(query: String, limit: Int = 5) : List<Search>

    @Delete(Search::class)
    suspend fun deleteHistory(search: Search)
}