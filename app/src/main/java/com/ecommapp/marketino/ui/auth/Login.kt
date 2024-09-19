package com.ecommapp.marketino.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        //referenece
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        loginbtn = findViewById(R.id.signInBtn)
        optionRegister = findViewById(R.id.option_register)

        // Set up text watchers for email and password input
        setupTextWatchers()

        // Observe StateFlows for changes
        observeViewModel()


        //onclick Listeners
        optionRegister.setOnClickListener {
            navigateToRegistration()
        }


        loginbtn.setOnClickListener {
            viewModel.onCLickLogin(this)
        }


    }


    override fun onStart() {
        super.onStart()
        // Set initial text for the email field
        userEmail.editText?.setText(viewModel.emailStateFlow.value)
        userPassword.editText?.setText(viewModel.passwordStateFlow.value)
    }

    private fun setupTextWatchers() {
        // Email text watcher
        userEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Password text watcher
        userPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginResponse.collect { response ->
                if(response != null)
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegistration() {
        val intent = Intent(this, Registration::class.java)
        startActivity(intent)
    }
}