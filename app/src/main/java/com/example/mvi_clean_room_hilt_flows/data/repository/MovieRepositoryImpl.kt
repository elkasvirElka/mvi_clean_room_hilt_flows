package com.example.mvi_clean_room_hilt_flows.data.repository

import androidx.room.withTransaction
import com.example.mvi_clean_room_hilt_flows.data.local.MovieDatabase
import com.example.mvi_clean_room_hilt_flows.data.local.dao.MovieInfoDao
import com.example.mvi_clean_room_hilt_flows.data.remote.ApiService
import com.example.mvi_clean_room_hilt_flows.domain.LoadType
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import com.example.mvi_clean_room_hilt_flows.domain.Resource
import com.example.mvi_clean_room_hilt_flows.domain.model.MovieInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okio.IOException
import retrofit2.HttpException

class MovieRepositoryImpl(
    private val service: ApiService,
    private val database: MovieDatabase
) : MovieRepository {

    //we get data always from database to show it on UI - this principle is called "single source of truth"
    override fun getMovies(loadType: LoadType): Flow<Resource<List<MovieInfo>>> = flow {
        emit(Resource.Loading())
        val dao = database.dao
        val moviesInfo = dao.getMoviesInfo().map { it.toMovieInfo() }
        emit(Resource.Loading(data = moviesInfo))
        try {
            when(loadType){
                LoadType.Refresh -> {
                    val remoteMoviesInfo = service.getMovies()
                    remoteMoviesInfo.body()?.let {
                        database.withTransaction {
                            dao.deleteAllMovies()
                            dao.insertMovies(it.results.map { it.toMovieInfoEntity() })
                        }
                    }
                }
                LoadType.LoadMore -> {
                    loadMore(dao)
                }
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Oops, something went wrong!", data = moviesInfo))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server, check your internet connection.", data = moviesInfo))
        }
        val newMoviesInfo = dao.getMoviesInfo().map { it.toMovieInfo() }
        emit(Resource.Success(newMoviesInfo))
    }

    val mutex = Mutex()
    private suspend fun loadMore(dao: MovieInfoDao) = mutex.withLock {
        var pagingSource = dao.pagingSource()
        println("loadMore page: $pagingSource")
        val page =  (pagingSource + 1) / 20 + 1
        println("loadMore page: $page")
        val remoteMoviesInfo = service.getMovies(page = page)
        remoteMoviesInfo.body()?.let {
            println("loadMore : ${it.results.first().title}")
            dao.insertMovies(it.results.map { it.toMovieInfoEntity() })
            var page2 =  dao.pagingSource() / 20 + 1
            println("loadMore insertMovies page: $page2")
        }

    }
}
