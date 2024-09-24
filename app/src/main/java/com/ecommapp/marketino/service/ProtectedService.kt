package com.ecommapp.marketino.service

import com.ecommapp.marketino.data.category.CategoryResponse
import com.ecommapp.marketino.data.products.ProductResponse
import retrofit2.http.GET

interface ProtectedService {
    @GET("user/product")
    suspend fun product(): ProductResponse

    @GET("user/category")
    suspend fun getCategory(): CategoryResponse

}