package com.example.mvi_clean_room_hilt_flows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvi_clean_room_hilt_flows.data.local.converters.IntListConverter
import com.example.mvi_clean_room_hilt_flows.data.local.dao.MovieInfoDao
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity

const val ROOM_VERSION = 1

@Database(entities = [MovieInfoEntity::class], version = ROOM_VERSION)
@TypeConverters(IntListConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val dao: MovieInfoDao
}
