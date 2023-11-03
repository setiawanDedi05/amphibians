package com.example.amphibians.data

import com.example.amphibians.model.Amphiby
import com.example.amphibians.network.AmphibiansApiService

interface AmphibiansRepository{
    suspend fun getAmphibyImage() : List<Amphiby>
}

class NetworkAmphibyImageRepository(
    private val amphibyService: AmphibiansApiService
) : AmphibiansRepository{
    override suspend fun getAmphibyImage(): List<Amphiby> = amphibyService.getAmphibians()
}