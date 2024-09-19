package com.ecommapp.marketino.api

import com.ecommapp.marketino.service.AuthService
import com.ecommapp.marketino.service.ProtectedService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GuestApiClient {
    val api: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl("https://sample-ecom.parallaxlogic.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}

object TokenApiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val token = "dataStoreManager.getBoolean"
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    // Retrofit instance with custom OkHttpClient
    val api: ProtectedService by lazy {
        Retrofit.Builder()
            .baseUrl("https://sample-ecom.parallaxlogic.dev/api/")
            .client(okHttpClient) // Add OkHttpClient to Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProtectedService::class.java)
    }
}