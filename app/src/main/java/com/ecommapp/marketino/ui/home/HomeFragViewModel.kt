package com.ecommapp.marketino.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.GuestApiClient
import com.ecommapp.marketino.api.ProtectedApiClient
import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.product.ProductsResponse
import com.ecommapp.marketino.data.products.ProductResponse
import com.ecommapp.marketino.repository.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeFragViewModel(application: Application) : AndroidViewModel(application){
    val productsResponse = MutableStateFlow<ProductResponse?>(null)

    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    private val productRepository = ProductRepo(ProtectedApiClient.api)

    init {
        getProducts()
    }
    private fun getProducts() {
        viewModelScope.launch {
            productRepository.getProducts(
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        productsResponse.value = resource.data // Set news data
                        Log.d("HomeFragViewModel", resource.data.toString())

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