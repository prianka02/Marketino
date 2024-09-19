package com.ecommapp.marketino.data.authentication.login

data class LoginResponse(
    val message: String?,
    val token: String?,
    val user: User?
)