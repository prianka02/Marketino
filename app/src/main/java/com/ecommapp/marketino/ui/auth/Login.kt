package com.ecommapp.marketino.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import com.ecommapp.marketino.ui.View.MainViewModel
import com.ecommapp.marketino.ui.home.HomeActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var userEmail: TextInputLayout
    private lateinit var userPassword: TextInputLayout
    private lateinit var loginbtn: MaterialButton
    private lateinit var optionRegister: TextView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        loginbtn = findViewById(R.id.signInBtn)
        optionRegister = findViewById(R.id.option_register)

        optionRegister.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val registrationResponse = intent.getParcelableExtra<RegistrationResponse>("REGISTER")

        Log.d("RegistrationResponse", registrationResponse.toString())

//        registrationStatus = intent.getParcelableExtra("REGISTER")!!

        loginbtn.setOnClickListener {
            val email = userEmail.editText?.text.toString()
            val password = userPassword.editText?.text.toString()

            // Call the validation function
            val valid = validateForm(email, password)



            if (valid) {
                viewModel.onCLickLogin(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields correctly!", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        lifecycleScope.launch {
            viewModel.loginResponse.collect { response ->
                if (registrationResponse?.status == 200) {
                    Log.d("Login", response.toString())
                    navigateToHome()

                } else {
//                    Toast.makeText(this@Login, "Please Registered First", Toast.LENGTH_SHORT).show()
//                    navigateToRegistration()
                }

            }
        }

    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java).apply {
        }

        startActivity(intent)
    }

    private fun navigateToRegistration() {
        val intent = Intent(this, Registration::class.java).apply {
        }

        startActivity(intent)
    }

    private fun validateForm(
        email: String,
        password: String,
    ): Boolean {
        var isValid = true

        // Validate email
        if (email.isEmpty() || !isValidEmail(email)) {
            userEmail.error = "Please enter a valid email address"
            isValid = false
        } else {
            userEmail.error = null
        }

        // Validate password
        if (password.isEmpty() || password.length < 6) {
            userPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            userPassword.error = null
        }

        // If all fields are valid, show success message
        if (isValid) {
//            navigateToHome()
//            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            // Clear the form fields
            userEmail.editText?.text?.clear()
            userPassword.editText?.text?.clear()
            return true

        } else {
            Toast.makeText(this, "Please fill in all fields correctly!", Toast.LENGTH_SHORT).show()
            return false

        }

    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}