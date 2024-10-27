package com.example.mvi_clean_room_hilt_flows.domain.model

import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity
import java.io.Serializable

//this is classes that describes remote data, but we can create another data classes for app and create mapper if needed
data class MovieInfo(
    val adult: Boolean,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>,
    val id: Int,
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
) : Serializable {
    fun toMovieInfoEntity(): MovieInfoEntity {
        return MovieInfoEntity(
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
            backdrop_path = backdrop_path?:""
        )
    }
}

data class MovieResult(
    val page: Int,
    val results: List<MovieInfo>,
    val total_pages: Int,
    val total_results: Int
) : Serializable

