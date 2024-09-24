package com.ecommapp.marketino.api

import android.util.Log
import com.ecommapp.marketino.service.AuthService
import com.ecommapp.marketino.service.ProtectedService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BaseURl = "https://sample-ecom.parallaxlogic.dev/api/"

object GuestApiClient {
    val api: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseURl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}

object ProtectedApiClient {
    // Token to be injected later
    private var authToken: String? = null

    fun updateToken(token: String?) {
        authToken = token
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            // Fetch the token from the injected value
            val token = authToken ?: ""

            Log.d("TOKEN Intercept", "$authToken")
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    // Retrofit instance with custom OkHttpClient
    val api: ProtectedService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseURl)
            .client(okHttpClient) // Add OkHttpClient to Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProtectedService::class.java)
    }
}