package com.littlelemon.interviewapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface StocksApiService {
    @GET("instruments.json")
    suspend fun getData(
    ) : List<StocksResponce>
}