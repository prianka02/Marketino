package com.ecommapp.marketino.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommapp.marketino.R
import com.ecommapp.marketino.ui.View.MainViewModel
import com.ecommapp.marketino.ui.auth.Login
import com.ecommapp.marketino.ui.auth.Registration
import com.ecommapp.marketino.ui.home.HomeActivity
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Start handling splash screen in ViewModel
        viewModel.handleSplashScreen()

       // collect the Handling Screen's response
        lifecycleScope.launch {
            viewModel.splashScreenComplete.collect { isComplete ->
                if (isComplete) {
                    checkLoginStatusAndNavigate()
                }
            }
        }

    }
    private fun checkLoginStatusAndNavigate() {

        Toast.makeText(this, "checkLoginStatusAndNavigate", Toast.LENGTH_SHORT).show()



        lifecycleScope.launch {
            viewModel.checkLoginStatus().collect { isLoggedIn ->
                if (isLoggedIn == true) {
                    // Navigate to HomeActivity if logged in


                    navigateToHome()
                } else {
                    // Navigate to LoginActivity if not logged in
                    navigateToLogin()
                }
            }
        }
    }


    private fun navigateToHome() {

        Toast.makeText(this, "navigateToHome", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        Toast.makeText(this, "navigateToLogin", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}

