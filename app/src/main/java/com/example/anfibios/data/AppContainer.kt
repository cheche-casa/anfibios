package com.example.anfibios.data

import com.example.anfibios.network.AnfibiosApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val anfibiosRepository: AnfibiosRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"

    //Non está no exemplo anterior
    @kotlinx.serialization.ExperimentalSerializationApi

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: AnfibiosApiService by lazy {
        retrofit.create(AnfibiosApiService::class.java)
    }

    override val anfibiosRepository: AnfibiosRepository by lazy {
        NetworkAnfibiosRepository(retrofitService)
    }
}

