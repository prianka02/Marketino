package com.ecommapp.marketino.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommapp.marketino.R
import com.ecommapp.marketino.datasource.DataStoreKeys
import com.ecommapp.marketino.ui.view.MainViewModel
import com.ecommapp.marketino.ui.auth.Login
import com.ecommapp.marketino.ui.home.HomeActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]


        init()

    }

    fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            checkLogin()
        }, 3000)
    }
    fun checkLogin() {
        lifecycleScope.launch {
            viewModel.tokenFlow.collect { token ->

                Log.d("SPLASH",token.toString())
                if (token != null) {
                    Log.d("SPLASH","IF")
                    navigateToHome()
                } else {
                    Log.d("SPLASH","ELSE")
                    navigateToLogin()
                }
            }
        }
    }



    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}

