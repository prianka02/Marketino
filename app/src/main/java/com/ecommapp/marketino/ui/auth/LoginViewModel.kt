package com.ecommapp.marketino.ui.auth

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.GuestApiClient
import com.ecommapp.marketino.api.ProtectedApiClient

import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.authentication.login.LoginRequest
import com.ecommapp.marketino.data.authentication.login.LoginResponse
import com.ecommapp.marketino.datasource.DataStoreKeys
import com.ecommapp.marketino.datasource.DatastoreManager
import com.ecommapp.marketino.repository.AuthRepo
import com.ecommapp.marketino.utils.Utility.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    // Variables
    val emailStateFlow = MutableStateFlow<String>("")
    val passwordStateFlow = MutableStateFlow<String>("")

    //API Response
    val loginResponse = MutableStateFlow<LoginResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow(false)

    //DI
    private val authRepo = AuthRepo(GuestApiClient.api)
    private val dataStoreManager = DatastoreManager(application)


    // Update email
    fun onEmailChanged(email: String) {
        emailStateFlow.value = email
    }

    // Update password
    fun onPasswordChanged(password: String) {
        passwordStateFlow.value = password
    }

    fun validateForm(): Boolean {
        val email = emailStateFlow.value
        val password = passwordStateFlow.value

        var isValid = true

        // Validate email
        if (email.isEmpty() || !isValidEmail(email)) {
            isValid = false
        } else {
        }

        // Validate password
        if (password.isEmpty() || password.length < 6) {
            isValid = false
        } else {
        }

        // If all fields are valid, show success message
        if (isValid) {
            return true

        } else {
//            Toast.makeText(this, "Please fill in all fields correctly!", Toast.LENGTH_SHORT).show()
            return false
        }

    }

    fun onCLickLogin(context: Context) {
        if (validateForm()) {

            Log.d("Viewmodel", " IF")
            val loginInstance = LoginRequest(
                user_id = emailStateFlow.value,
                password = passwordStateFlow.value
            )
            Log.d("Viewmodel", loginInstance.toString())
            viewModelScope.launch {
                authRepo.login(
                    loginInstance
                ).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            isLoading.value = true // Set loading state
                        }

                        is Resource.Success -> {
                            isLoading.value = false // Stop loading
                            loginResponse.value = resource.data // Set news data
                            Log.d("Viewmodel", resource.data.toString())



                            resource.data.token?.let {
                                dataStoreManager.saveString(DataStoreKeys.token, it)
                                // Inject token into the API client
                                ProtectedApiClient.updateToken(it)
                            }


                        }

                        is Resource.Error -> {
                            isLoading.value = false // Stop loading
                            errorMessage.value = true // Set error message
                            Log.d("Login Response", resource.message)
                        }
                    }
                }
            }
        } else {

            Log.d("Viewmodel", " else")
            Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show()
        }
    }

}



