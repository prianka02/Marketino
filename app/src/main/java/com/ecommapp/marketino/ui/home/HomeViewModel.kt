package com.ecommapp.marketino.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.ProtectedApiClient

import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.products.ProductResponse
import com.ecommapp.marketino.datasource.DatastoreManager
import com.ecommapp.marketino.repository.ProtectedRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val count = MutableLiveData<Int>(0)
    val liveResponse = MutableLiveData<ProductResponse?>(null)
    val productResponseFlow = MutableStateFlow<ProductResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    //DI
    private val protectedRepo = ProtectedRepo(ProtectedApiClient.api)
    private val dataStoreManager = DatastoreManager(application)

    init {
        getProducts()
    }


    fun onIncrement(){
        count.value = count.value?.plus(1)
    }

    fun onDecrement(){
        count.value = count.value?.minus(1)
    }

    fun getProducts() {
        viewModelScope.launch {
            protectedRepo.getProducts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        productResponseFlow.value = resource.data // Set news data
                        liveResponse.value = resource.data
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



