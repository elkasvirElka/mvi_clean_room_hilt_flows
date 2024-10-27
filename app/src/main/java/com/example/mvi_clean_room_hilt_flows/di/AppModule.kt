package com.example.mvi_clean_room_hilt_flows.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.mvi_clean_room_hilt_flows.data.local.MovieDatabase
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity
import com.example.mvi_clean_room_hilt_flows.data.network.Retrofit
import com.example.mvi_clean_room_hilt_flows.data.remote.ApiService
import com.example.mvi_clean_room_hilt_flows.data.remote.MovieRemoteMediator
import com.example.mvi_clean_room_hilt_flows.data.remote.MovieRepositoryImpl
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
//ServiceComponent, ViewModelComponent, RetainComponent are the components that can be installed instead of SingletonComponent
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMyApi(): ApiService {
        return  Retrofit.getRetrofit().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieInfoDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app, MovieDatabase::class.java, "word_db"
        )
            //.addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideBeerPager(database: MovieDatabase,api: ApiService): Pager<Int, MovieInfoEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(
                database = database,
                api = api
            ),
            pagingSourceFactory = {
                database.dao.pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    //if context needed in params you can add app: Apllication
    fun provideMyRepository(api: ApiService,
                            db: MovieDatabase): MovieRepository{
        return MovieRepositoryImpl(api, database = db)
    }

}
