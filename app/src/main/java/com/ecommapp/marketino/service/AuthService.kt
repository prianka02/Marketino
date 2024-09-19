package com.ecommapp.marketino.service

import com.ecommapp.marketino.data.authentication.login.LoginRequest
import com.ecommapp.marketino.data.authentication.login.LoginResponse
import com.ecommapp.marketino.data.authentication.register.CreateRegistration
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("user/register")
    suspend fun createAccount(
        @Body body: CreateRegistration
    ): RegistrationResponse

    @POST("user/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

}