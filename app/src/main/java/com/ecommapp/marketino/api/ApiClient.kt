package com.ecommapp.marketino.api

import com.ecommapp.marketino.service.ProductService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GuestApiClient {
    val api: ProductService by lazy {
        Retrofit.Builder()
            .baseUrl("https://sample-ecom.parallaxlogic.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }
}

object ProtectedApiClient {

 // OkHttpClient with AuthInterceptor to add the token in headers
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val token = "tokenProvider.getToken()" //get token
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    // Retrofit instance with custom OkHttpClient
    val api: ProductService by lazy {
        Retrofit.Builder()
            .baseUrl("https://sample-ecom.parallaxlogic.dev/api/")
            .client(okHttpClient) // Add OkHttpClient to Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }
}