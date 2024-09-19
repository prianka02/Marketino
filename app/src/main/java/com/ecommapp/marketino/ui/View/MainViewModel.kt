package com.ecommapp.marketino.ui.View

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.GuestApiClient

import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.api.TokenApiClient
import com.ecommapp.marketino.data.authentication.login.LoginRequest
import com.ecommapp.marketino.data.authentication.login.LoginResponse
import com.ecommapp.marketino.data.authentication.register.CreateRegistration
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import com.ecommapp.marketino.datasource.DatastoreManager
import com.ecommapp.marketino.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    val registrationResponse = MutableStateFlow<RegistrationResponse?>( null )
    val loginResponse = MutableStateFlow<LoginResponse?>( null )

    // Separate variables to handle loading and error states
    val isLoading = MutableStateFlow(true)

    // Separate variables to handle loading and error states
    val errorMessage = MutableStateFlow<String?>(null)

    //    Variables for handling SplashScreen's Handler time
    private val _splashScreenComplete = MutableStateFlow(false)
    val splashScreenComplete = _splashScreenComplete



    private val authRepo = AuthRepo(GuestApiClient.api)
    private val dataStoreManager = DatastoreManager(application)


    fun onCreateAccount(
        userName: String,
        userPhone: String,
        userEmail: String,
        userPassword: String
    ) {
        val newInstance = CreateRegistration(
            name = userName,
            phone = userPhone,
            email = userEmail,
            password = userPassword
        )

        viewModelScope.launch {
            authRepo.createRegistration(
                newInstance
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        registrationResponse.value = resource.data // Set news data
                        Log.d("Viewmodel createRegistration", resource.data.toString())
                        Log.d("ViewModel", newInstance.name!!)

//                        saveCredentials(userName, userPhone, userEmail, userPassword)
                    }

                    is Resource.Error ->{
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }

    }

    fun onCLickLogin(
        userEmail: String,
        userPassword: String
    ) {
        val loginInstance = LoginRequest(
            email = userEmail,
            password = userPassword
        )


        Log.d("Viewmodel","onCLickLogin")
        Log.d("Viewmodel",loginInstance.toString())
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

                        resource.data.token?.let { saveCredentials(it) }
                    }

                    is Resource.Error ->{
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }

//  Save Generated Token after the login account
    private suspend fun saveCredentials(token: String) {

        dataStoreManager.saveString("TOKEN", token)
         Log.d("Token", token)

        dataStoreManager.saveBoolean("IS_LOGGED_IN", true)
    }
// Check the user is LOGGED IN or NOT
    fun checkLoginStatus() = dataStoreManager.getBoolean("IS_LOGGED_IN", false)


    // Handle the SplashScreen logic here
    fun handleSplashScreen() {
        viewModelScope.launch {
            Handler(Looper.getMainLooper()).postDelayed({
                _splashScreenComplete.value = true
            }, 3000)
        }
    }


  }



