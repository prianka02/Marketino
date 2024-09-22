package com.ecommapp.marketino.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.ecommapp.marketino.R
import com.ecommapp.marketino.ui.splash.SplashViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


    }
}