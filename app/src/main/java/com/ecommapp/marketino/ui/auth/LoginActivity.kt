package com.ecommapp.marketino.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommapp.marketino.R
import com.ecommapp.marketino.ui.home.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var userEmail: TextInputLayout
    private lateinit var userPassword: TextInputLayout
    private lateinit var loginbtn: MaterialButton
    private lateinit var optionRegister: TextView
    private lateinit var errorText: TextView
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        //references
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        loginbtn = findViewById(R.id.signInBtn)
        optionRegister = findViewById(R.id.option_register)
        errorText = findViewById(R.id.error_msz)

        // Set up text watchers for email and password input
        setupTextWatchers()

        //onclick Listeners
        optionRegister.setOnClickListener {
            navigateToRegistration()
        }

//        Button clicked listener for Login
        loginbtn.setOnClickListener {
            viewModel.onCLickLogin(this)
            // Observe StateFlows for changes
            observeViewModel()
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
                if(response != null) {
                    navigateToHome()
                    Log.d("response", response.toString())
                }
            }
        }
        lifecycleScope.launch {
            viewModel.errorMessage.collect { isError ->
                if(isError) {
                    errorText.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegistration() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}