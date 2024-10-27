package com.example.mvi_clean_room_hilt_flows.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity

@Dao
interface MovieInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieInfoEntity>)
    /**
     *  is generally more efficient for bulk operations or frequent conflicts, as it avoids the delete-then-insert behavior of REPLACE.
     * or you can use upsertAll: insert or update
     * @Upsert combines the insert and update functionality,
     * optimizing the operation and often performing better than @Insert(onConflict = REPLACE) when handling large lists or frequent conflicts.
     */
    @Upsert
    suspend fun upsertAll(movies: List<MovieInfoEntity>)

    @Query("DELETE FROM movieinfoentity WHERE title IN(:title)")
    suspend fun deleteMovies(title: List<String>)

    @Query("DELETE FROM movieinfoentity")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movieinfoentity WHERE title LIKE'%' || :title || '%'")
    suspend fun getMoviesInfo(title: String): List<MovieInfoEntity>

    @Query("SELECT * FROM movieinfoentity")
    fun pagingSource(): PagingSource<Int, MovieInfoEntity>


    @Query("SELECT * FROM movieinfoentity")
    suspend fun getMoviesInfo(): List<MovieInfoEntity>
}
