package com.example.mvi_clean_room_hilt_flows.data.repository

import com.example.mvi_clean_room_hilt_flows.data.remote.ApiService
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import com.example.mvi_clean_room_hilt_flows.domain.entity.MovieResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepositoryImpl(private val service: ApiService): MovieRepository {

    override suspend fun getMovies(): Response<MovieResult> {
        return withContext(Dispatchers.IO) {
            service.getMovies()
        }
    }
}
