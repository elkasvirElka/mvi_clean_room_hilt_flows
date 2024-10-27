package com.example.mvi_clean_room_hilt_flows.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity

@Dao
interface MovieInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieInfoEntity>)

    @Query("DELETE FROM movieinfoentity WHERE title IN(:title)")
    suspend fun deleteMovies(title: List<String>)

    @Query("DELETE FROM movieinfoentity")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movieinfoentity WHERE title LIKE'%' || :title || '%'")
    suspend fun getMoviesInfo(title: String): List<MovieInfoEntity>

    @Query("SELECT * FROM movieinfoentity")
    suspend fun getMoviesInfo(): List<MovieInfoEntity>

    @Query("SELECT COUNT(*) FROM movieinfoentity")
    suspend fun pagingSource(): Int
}
