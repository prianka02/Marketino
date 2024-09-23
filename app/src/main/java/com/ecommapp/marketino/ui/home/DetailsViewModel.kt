package com.ecommapp.marketino.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.api.ProtectedApiClient
import com.ecommapp.marketino.api.Resource
import com.ecommapp.marketino.data.products.Product
import com.ecommapp.marketino.data.products.ProductResponse
import com.ecommapp.marketino.datasource.DatastoreManager
import com.ecommapp.marketino.repository.ProductRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application)  {

    val productsView = MutableStateFlow<Product?>(null)


    fun showProducts() {
        viewModelScope.launch {
            }
        }

    }
