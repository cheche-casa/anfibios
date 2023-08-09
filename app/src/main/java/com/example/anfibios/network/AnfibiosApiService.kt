package com.example.anfibios.network

import com.example.anfibios.model.Anfibio
import retrofit2.http.GET

interface AnfibiosApiService {
        @GET("amphibians") //Este é o subcartafol da URI
        suspend fun getAnfibios(): List<Anfibio>
}