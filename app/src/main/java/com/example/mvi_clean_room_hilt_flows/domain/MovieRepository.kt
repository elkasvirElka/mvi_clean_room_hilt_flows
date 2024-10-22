package com.example.mvi_clean_room_hilt_flows.domain

import com.example.mvi_clean_room_hilt_flows.domain.entity.MovieResult
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(): Response<MovieResult>
}
