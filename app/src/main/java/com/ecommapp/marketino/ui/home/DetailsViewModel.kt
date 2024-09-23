package com.ecommapp.marketino.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ecommapp.marketino.data.products.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application)  {

    val productsView = MutableStateFlow<Product?>(null)


    fun showProducts() {
        viewModelScope.launch {
        }
    }

}
