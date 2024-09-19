package com.ecommapp.marketino.api

import com.ecommapp.marketino.service.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val api: ProductService by lazy {
        Retrofit.Builder()
            .baseUrl("https://sample-ecom.parallaxlogic.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }
}