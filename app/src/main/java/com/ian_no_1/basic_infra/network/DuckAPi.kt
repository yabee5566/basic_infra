package com.ian_no_1.basic_infra.network

import com.ian_no_1.basic_infra.network.response.DuckData
import retrofit2.http.GET

interface DuckApi {
    @GET("https://random-d.uk/api/quack")
    suspend fun getRandomDuck(): DuckData
}