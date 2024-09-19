package com.ecommapp.marketino.ui.splash

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.GuestApiClient

import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.api.TokenApiClient
import com.ecommapp.marketino.data.authentication.login.LoginRequest
import com.ecommapp.marketino.data.authentication.login.LoginResponse
import com.ecommapp.marketino.data.authentication.register.CreateRegistration
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import com.ecommapp.marketino.datasource.DataStoreKeys
import com.ecommapp.marketino.datasource.DatastoreManager
import com.ecommapp.marketino.repository.AuthRepo
import com.ecommapp.marketino.ui.auth.Login
import com.ecommapp.marketino.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val tokenFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val dataStoreManager = DatastoreManager(application)

    init {
        // Launch coroutine to collect the token from DataStore
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.getString(DataStoreKeys.token, null)
                .collect { token ->
                    tokenFlow.value = token  // Update the MutableStateFlow with token
                }
        }
    }

}



