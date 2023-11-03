package com.example.amphibians.network

import com.example.amphibians.model.Amphiby
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphiby>
}