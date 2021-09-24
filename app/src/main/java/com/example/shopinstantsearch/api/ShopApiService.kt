package com.example.shopinstantsearch.api

import com.example.shopinstantsearch.data.ShopInfo
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:5000/api/Shopapi/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ShopApiService {
    @GET("shops")
    suspend fun getShops(): List<ShopInfo>
}

object ShopApi {
    val retrofitService : ShopApiService by lazy {
        retrofit.create(ShopApiService::class.java)
    }
}