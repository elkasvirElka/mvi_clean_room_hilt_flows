package com.example.mvi_clean_room_hilt_flows.data.network

import android.content.Context
import okhttp3.OkHttpClient

class OhHttpProvider {
    companion object {
        fun provideClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor()).build()
        }
    }
}
