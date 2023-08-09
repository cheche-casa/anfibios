package com.example.anfibios.data

import com.example.anfibios.model.Anfibio
import com.example.anfibios.network.AnfibiosApiService

interface AnfibiosRepository {
    suspend fun getAnfibios(): List<Anfibio>
}

class NetworkAnfibiosRepository(
    private val anfibiosApiService: AnfibiosApiService
) : AnfibiosRepository {
    override suspend fun getAnfibios(): List<Anfibio> = anfibiosApiService.getAnfibios()
}
