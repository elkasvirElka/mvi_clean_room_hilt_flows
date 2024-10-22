package com.example.mvi_clean_room_hilt_flows.data.network

import android.content.Context
import com.example.mvi_clean_room_hilt_flows.data.network.OhHttpProvider.Companion.provideClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class Retrofit {
    companion object {
        private lateinit var sRetrofit: Retrofit
        fun getRetrofit(): Retrofit {
            if (!Companion::sRetrofit.isInitialized) {
                sRetrofit = Retrofit.Builder()
                    .client(provideClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return sRetrofit
        }
    }
}
