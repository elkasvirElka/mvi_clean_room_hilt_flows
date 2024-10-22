package com.example.mvi_clean_room_hilt_flows.data.remote

import com.example.mvi_clean_room_hilt_flows.domain.entity.MovieResult
import retrofit2.Response
import retrofit2.http.GET

interface ApiService  {

    @GET("discover/movie")
    suspend fun getMovies(): Response<MovieResult>
}
