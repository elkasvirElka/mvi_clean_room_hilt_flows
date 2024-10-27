package com.example.mvi_clean_room_hilt_flows.domain.use_case

import com.example.mvi_clean_room_hilt_flows.domain.LoadType
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import com.example.mvi_clean_room_hilt_flows.domain.Resource
import com.example.mvi_clean_room_hilt_flows.domain.model.MovieInfo
import kotlinx.coroutines.flow.Flow

class GetMovieInfo(private val repository: MovieRepository, private val loadType: LoadType) {

    operator fun invoke(): Flow<Resource<List<MovieInfo>>>{
        //here we can do some logic before returning the data. For example data validation
        return repository.getMovies(loadType)
    }
}
