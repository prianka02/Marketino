package com.ecommapp.marketino.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ecommapp.marketino.R
import com.ecommapp.marketino.data.authentication.register.RegistrationResponse
import com.ecommapp.marketino.ui.View.MainViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class Registration : AppCompatActivity() {
    private lateinit var userName: TextInputLayout
    private lateinit var phoneNumber: TextInputLayout
    private lateinit var userEmail: TextInputLayout
    private lateinit var userPassword: TextInputLayout
    private lateinit var reEnterPassword: TextInputLayout
    private lateinit var registerbtn: MaterialButton
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        userName = findViewById(R.id.user_name)
        phoneNumber = findViewById(R.id.phone_no)
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        reEnterPassword = findViewById(R.id.re_enter_password)
        registerbtn = findViewById(R.id.signUpBtn)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        registerbtn.setOnClickListener {
            val name = userName.editText?.text.toString()
            val phoneNo = phoneNumber.editText?.text.toString()
            val email = userEmail.editText?.text.toString()
            val password = userPassword.editText?.text.toString()
            val reEnterPass = reEnterPassword.editText?.text.toString()

            // Call the validation function
            val valid = validateForm(name, phoneNo, email, password, reEnterPass)
println("valid $valid")
            if (valid) {
                viewModel.onCreateAccount(name, phoneNo, email, password)

                lifecycleScope.launch {
                    viewModel.registrationResponse.collect { response ->
                        println("response ${response}");
                        if (response != null) {
                            if (response.status == 200) {
                                Toast.makeText(this@Registration, response.message, Toast.LENGTH_SHORT).show()
                                Log.d("Registration", response.toString())
                                navigateToLogin(response)
                                finish()
                            } else {
                                Log.d("Registration 1", response.status.toString());

                                Toast.makeText(this@Registration, response.message, Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                }
            }

        }
    }


    private fun navigateToLogin(response: RegistrationResponse) {
        val intent = Intent(this, Login::class.java).apply {
            putExtra("REGISTER", response)
        }

        startActivity(intent)
        finish()
    }

    private fun validateForm(
        username: String,
        phone: String,
        email: String,
        password: String,
        reenteredPassword: String
    ): Boolean {
        var isValid = true

        // Validate username
        if (username.isEmpty()) {
            userName.error = "Please enter a username"
            isValid = false
        } else {
            userName.error = null
        }

        // Validate phone number
        if (phone.isEmpty() || phone.length != 10 || phone[0] != '1') {
            phoneNumber.error = "Please enter a valid phone number"
            isValid = false
        } else {
            phoneNumber.error = null
        }

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

        // Validate re-entered password
        if (reenteredPassword.isEmpty() || reenteredPassword != password) {
            reEnterPassword.error = "Passwords do not match"
            isValid = false
        } else {
            reEnterPassword.error = null
        }

        // If all fields are valid, show success message
        if (isValid) {
//             Clear the form fields
            userName.editText?.text?.clear()
            phoneNumber.editText?.text?.clear()
            userEmail.editText?.text?.clear()
            userPassword.editText?.text?.clear()
            reEnterPassword.editText?.text?.clear()
            return true

        } else {
            Toast.makeText(this, "Please fill in all fields correctly!", Toast.LENGTH_SHORT).show()
        }
        return false
    }


        private fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

}


