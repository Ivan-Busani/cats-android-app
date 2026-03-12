package com.mivanba.catsapp.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getCats(
        @Query("limit") limit: Int = 15,
        @Query("page") page: Int = 0,
        @Query("has_breeds") hasBreeds: Int = 1
    ): List<Cat>

    @GET("v1/images/{image_id}")
    suspend fun getCatDetails(
        @Path("image_id") id: String
    ): Cat
}

object RetrofitClient {
    private const val BASE_URL = "https://api.thecatapi.com/"
    private const val API_KEY = "live_wvadQ9OtQG6jTPoMWdknJQyNPgsIMaFXisHXNaDYeb3O8PlF4RbVkkdN0y1R9bHm"

    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .addHeader("x-api-key", API_KEY)
            .build()

        chain.proceed(newRequest)
    }.build()

    val apiService: CatApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApiService::class.java)
    }
}