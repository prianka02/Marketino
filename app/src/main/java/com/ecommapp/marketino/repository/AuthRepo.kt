package com.ecommapp.marketino.repository

import android.util.Log
import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.authentication.login.LoginRequest
import com.ecommapp.marketino.data.authentication.login.LoginResponse
import com.ecommapp.marketino.data.authentication.register.CreateRegistration
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import com.ecommapp.marketino.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepo(private val api: AuthService) {

    suspend fun createRegistration(
        user: CreateRegistration

    ): Flow<Resource<RegistrationResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.createAccount(user) // Make the network request
//            Log.d("Repository createRegistration ", response.toString())
//            println("Repository createRegistration  $response")
            emit(Resource.Success(response))

        } catch (e: Exception) {
            println("Repository createRegistration  error")
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)

    suspend fun login(
        user: LoginRequest
    ): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.login(user) // Make the network request
            Log.d("Repository", response.toString())
            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
            Log.d("Error", e.toString())
        }
    }.flowOn(Dispatchers.IO)

}