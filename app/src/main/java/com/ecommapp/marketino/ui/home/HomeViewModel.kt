package com.ecommapp.marketino.ui.home

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.GuestApiClient
import com.ecommapp.marketino.api.ProtectedApiClient

import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.authentication.login.LoginRequest
import com.ecommapp.marketino.data.authentication.login.LoginResponse
import com.ecommapp.marketino.data.authentication.register.CreateRegistration
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import com.ecommapp.marketino.data.product.ProductsResponse
import com.ecommapp.marketino.data.products.ProductResponse
import com.ecommapp.marketino.datasource.DataStoreKeys
import com.ecommapp.marketino.datasource.DatastoreManager
import com.ecommapp.marketino.repository.AuthRepo
import com.ecommapp.marketino.repository.ProductRepo
import com.ecommapp.marketino.ui.auth.Login
import com.ecommapp.marketino.ui.home.HomeActivity
import com.ecommapp.marketino.utils.Utility.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val productResponseFlow = MutableStateFlow<ProductResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    //DI
    private val productRepo = ProductRepo(ProtectedApiClient.api)
    private val dataStoreManager = DatastoreManager(application)

    init {
        getProducts()
    }


    fun getProducts() {
        viewModelScope.launch {
            productRepo.getProducts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        productResponseFlow.value = resource.data // Set news data
                        Log.d("Viewmodel", resource.data.toString())
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }

    }

}



