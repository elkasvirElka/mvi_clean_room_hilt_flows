package com.example.mvi_clean_room_hilt_flows.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvi_clean_room_hilt_flows.domain.model.MovieInfo

@Entity
data class MovieInfoEntity (
    @PrimaryKey val id: Int = 0,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int

){

    fun toMovieInfo(): MovieInfo {
        return MovieInfo(
            genre_ids = genre_ids,
            original_language = original_language,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            release_date = release_date,
            title = title,
            video = video,
            vote_average = vote_average,
            vote_count = vote_count,
            id = id,
            adult = adult,
            backdrop_path = backdrop_path)
    }
}
