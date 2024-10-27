package com.example.mvi_clean_room_hilt_flows.data.remote

import com.example.mvi_clean_room_hilt_flows.domain.model.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService  {

    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page: Int = 1): Response<MovieResult>
}
