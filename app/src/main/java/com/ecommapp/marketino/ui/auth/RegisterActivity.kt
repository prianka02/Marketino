package com.ecommapp.marketino.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommapp.marketino.R
import com.ecommapp.marketino.databinding.ActivityRegistrationBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var registrationBinding: ActivityRegistrationBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        ViewBinding added
        registrationBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(registrationBinding.root)

//      View model initialization
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]


        // Set up text watchers for email and password input
        setupTextWatchers()


        // Button clicked listener for Register
        registrationBinding.signUpBtn.setOnClickListener {
            viewModel.onCLickRegister(this)
            // Observe StateFlows for changes
            observeViewModel()
        }
    }

//    Implementations for rotate screen to set values
    override fun onStart() {
        super.onStart()
        // Set initial text for the text fields
        registrationBinding.userName.editText?.setText(viewModel.nameStateFlow.value)
        registrationBinding.userEmail.editText?.setText(viewModel.emailStateFlow.value)
        registrationBinding.phoneNo.editText?.setText(viewModel.phoneStateFlow.value)
        registrationBinding.userPassword.editText?.setText(viewModel.passwordStateFlow.value)
        registrationBinding.reEnterPassword.editText?.setText(viewModel.rePasswordStateFlow.value)
    }

    private fun setupTextWatchers() {

        // UserName text watcher
        registrationBinding.userName.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onNameChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Email text watcher
        registrationBinding.userEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // PhoneNO. text watcher
        registrationBinding.phoneNo.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPhoneNoChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Password text watcher
        registrationBinding.userPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChanged(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Re-Enter Password text watcher
        registrationBinding.reEnterPassword.editText?.addTextChangedListener(object : TextWatcher {
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
                    registrationBinding.errorMsz.visibility = View.VISIBLE
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