package com.example.mvi_clean_room_hilt_flows.di

import android.app.Application
import androidx.room.Room
import com.example.mvi_clean_room_hilt_flows.data.local.MovieDatabase
import com.example.mvi_clean_room_hilt_flows.data.network.Retrofit
import com.example.mvi_clean_room_hilt_flows.data.remote.ApiService
import com.example.mvi_clean_room_hilt_flows.data.repository.MovieRepositoryImpl
import com.example.mvi_clean_room_hilt_flows.domain.MovieRepository
import com.google.gson.Gson
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

    @Provides
    @Singleton
    //if context needed in params you can add app: Apllication
    fun provideMyRepository(api: ApiService,
                            db: MovieDatabase): MovieRepository{
        return MovieRepositoryImpl(api, database = db)
    }

}
