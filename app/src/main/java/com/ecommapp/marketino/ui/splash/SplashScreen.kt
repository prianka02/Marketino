package com.ecommapp.marketino.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommapp.marketino.R
import com.ecommapp.marketino.ui.auth.LoginActivity
import com.ecommapp.marketino.ui.home.MainActivity
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

