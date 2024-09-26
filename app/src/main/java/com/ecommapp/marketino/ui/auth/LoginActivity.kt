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
import com.ecommapp.marketino.databinding.ActivityLoginBinding
import com.ecommapp.marketino.ui.home.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        bind loginActivity in layout Inflater
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

//        Attach ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        // Set up text watchers for email and password input
        setupTextWatchers()

        //onclick Listeners
        loginBinding.optionRegister.setOnClickListener {
            navigateToRegistration()
        }

//        Button clicked listener for Login
        loginBinding.signInBtn.setOnClickListener {
            viewModel.onCLickLogin(this)
            // Observe StateFlows for changes
            observeViewModel()
        }
    }


    override fun onStart() {
        super.onStart()
        // Set initial text for the email field
        loginBinding.userEmail.editText?.setText(viewModel.emailStateFlow.value)
        loginBinding.userPassword.editText?.setText(viewModel.passwordStateFlow.value)
    }

    private fun setupTextWatchers() {
        // Email text watcher
        loginBinding.userEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Password text watcher
        loginBinding.userPassword.editText?.addTextChangedListener(object : TextWatcher {
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
                    loginBinding.errorMsz.visibility = View.VISIBLE
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