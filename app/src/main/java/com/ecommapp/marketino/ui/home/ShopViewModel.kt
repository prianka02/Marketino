package com.ecommapp.marketino.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.ProtectedApiClient
import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.category.CategoryResponse
import com.ecommapp.marketino.data.products.ProductResponse
import com.ecommapp.marketino.repository.ProtectedRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {
//    Api Responses in state flow
    val categoryResponseFlow = MutableStateFlow<CategoryResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

//   Protected Repository added
    private val protectedRepo = ProtectedRepo(ProtectedApiClient.api)

    init {
        getCategories()
    }


    fun getCategories() {
        viewModelScope.launch {
            protectedRepo.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        categoryResponseFlow.value = resource.data // Set news data
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