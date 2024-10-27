package com.example.mvi_clean_room_hilt_flows.domain

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}

sealed class LoadType {
    object Refresh: LoadType()
    object LoadMore: LoadType()
}