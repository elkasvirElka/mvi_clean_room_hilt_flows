package com.example.mvi_clean_room_hilt_flows.data.remote

import androidx.paging.PagingState
import androidx.room.withTransaction
import com.example.mvi_clean_room_hilt_flows.data.local.MovieDatabase
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import com.example.mvi_clean_room_hilt_flows.domain.Resource
import com.example.mvi_clean_room_hilt_flows.domain.model.MovieInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class MovieRepositoryImpl(
    private val service: ApiService,
    private val database: MovieDatabase
) : MovieRepository {

    //we get data always from database to show it on UI - this principle is called "single source of truth"
    override fun getMovies(): Flow<Resource<List<MovieInfo>>> = flow {
        emit(Resource.Loading())
        val dao = database.dao
        val moviesInfo = dao.getMoviesInfo().map { it.toMovieInfo() }
        emit(Resource.Loading(data = moviesInfo))

        try {
            val remoteMoviesInfo = service.getMovies()
            remoteMoviesInfo.body()?.let {
                database.withTransaction {
                    dao.deleteMovies(it.results.map { it.title })
                    dao.upsertAll(it.results.map { it.toMovieInfoEntity() })
                }
            }
            val newMoviesInfo = dao.getMoviesInfo().map { it.toMovieInfo() }
            emit(Resource.Success(newMoviesInfo))

        } catch (e: HttpException) {
            emit(Resource.Error(message = "Oops, something went wrong!", data = moviesInfo))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server, check your internet connection.", data = moviesInfo))
        }
    }

}
