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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var userName: TextInputLayout
    private lateinit var phoneNumber: TextInputLayout
    private lateinit var userEmail: TextInputLayout
    private lateinit var userPassword: TextInputLayout
    private lateinit var reEnterPassword: TextInputLayout
    private lateinit var registerbtn: MaterialButton
    private lateinit var errorText: TextView
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
//      View model initialization
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        userName = findViewById(R.id.user_name)
        phoneNumber = findViewById(R.id.phone_no)
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        reEnterPassword = findViewById(R.id.re_enter_password)
        errorText = findViewById(R.id.error_msz)
        registerbtn = findViewById(R.id.signUpBtn)


        // Set up text watchers for email and password input
        setupTextWatchers()


        // Button clicked listener for Register
        registerbtn.setOnClickListener {
            viewModel.onCLickRegister(this)
            // Observe StateFlows for changes
            observeViewModel()
        }
    }

//    Implementations for rotate screen to set values
    override fun onStart() {
        super.onStart()
        // Set initial text for the text fields
        userName.editText?.setText(viewModel.nameStateFlow.value)
        userEmail.editText?.setText(viewModel.emailStateFlow.value)
        phoneNumber.editText?.setText(viewModel.phoneStateFlow.value)
        userPassword.editText?.setText(viewModel.passwordStateFlow.value)
        reEnterPassword.editText?.setText(viewModel.rePasswordStateFlow.value)
    }

    private fun setupTextWatchers() {

        // UserName text watcher
        userName.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onNameChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Email text watcher
        userEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // PhoneNO. text watcher
        phoneNumber.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPhoneNoChanged(s.toString())
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

        // Re-Enter Password text watcher
        reEnterPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onRePasswordChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
//        Get Registration Responses
        lifecycleScope.launch {
            viewModel.registrationResponse.collect { response ->
                if (response != null) {
                    navigateToLogin()
                    Log.d("response", response.toString())
                }
            }
        }
//        Handle error responses
        lifecycleScope.launch {
            viewModel.errorMessage.collect { isError ->
                if(isError) {
                    errorText.visibility = View.VISIBLE
                }
            }
        }

    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)
        finish()
    }
}