package com.isaacdelosreyes.monumentscompose.core.data.remote.retrofit

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.MonumentsDto
import retrofit2.http.GET

interface MonumentWs {

    @GET("9f509c4b-1dd5-4a84-aec0-32b93d68f78b")
    suspend fun getAllMonuments(): MonumentsDto
}