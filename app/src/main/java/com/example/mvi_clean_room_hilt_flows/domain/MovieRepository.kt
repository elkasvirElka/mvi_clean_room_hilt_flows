package com.example.mvi_clean_room_hilt_flows.domain

import com.example.mvi_clean_room_hilt_flows.domain.model.MovieInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(loadType: LoadType): Flow<Resource<List<MovieInfo>>>
}
