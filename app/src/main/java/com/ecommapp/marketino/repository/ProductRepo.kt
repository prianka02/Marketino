package com.ecommapp.marketino.repository

import android.util.Log
import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.authentication.login.LoginRequest
import com.ecommapp.marketino.data.authentication.login.LoginResponse
import com.ecommapp.marketino.data.authentication.register.CreateRegistration
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import com.ecommapp.marketino.data.product.ProductsResponse
import com.ecommapp.marketino.data.products.ProductResponse
import com.ecommapp.marketino.service.AuthService
import com.ecommapp.marketino.service.ProtectedService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepo(private val api: ProtectedService) {

    suspend fun getProducts(): Flow<Resource<ProductResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.product() // Make the network request
            Log.d("Repository getProducts ", response.toString())
            emit(Resource.Success(response))

        } catch (e: Exception) {
            Log.d("Repository getProducts error ", e.toString())

            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)



}