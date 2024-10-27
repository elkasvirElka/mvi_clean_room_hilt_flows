package com.example.mvi_clean_room_hilt_flows.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mvi_clean_room_hilt_flows.data.local.MovieDatabase
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity
import com.example.mvi_clean_room_hilt_flows.domain.Resource
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class MovieRemoteMediator(private val database: MovieDatabase, private val api: ApiService): RemoteMediator<Int, MovieInfoEntity>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieInfoEntity>
    ): MediatorResult {
        val dao = database.dao
        val loadingKey =   when(loadType) {
            LoadType.REFRESH -> {
                1
            }
           LoadType.APPEND -> {
                 val lastItem = state.lastItemOrNull()
                 if(lastItem == null) {
                     1
                 }else{
                     //I can't use id because the number here is wrong
                     (lastItem.id / state.config.pageSize) + 1
                 }
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        try {
            val remoteMoviesInfo = api.getMovies()
            remoteMoviesInfo.body()?.let {
                database.withTransaction {
                    if(loadType == LoadType.REFRESH) {
                        dao.deleteAllMovies()
                    }
                    dao.upsertAll(it.results.map { it.toMovieInfoEntity() })
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = remoteMoviesInfo.body()?.results?.isEmpty() ?: true
            )

        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
        return MediatorResult.Success(endOfPaginationReached = true)
    }


}
