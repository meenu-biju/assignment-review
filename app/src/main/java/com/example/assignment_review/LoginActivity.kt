package com.example.assignment_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assignment_review.databinding.ActivityLoginBinding
import com.example.assignment_review.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            var et_email = findViewById<TextInputEditText>(R.id.et_email)
            var et_password = findViewById<TextInputEditText>(R.id.et_password)
            if(et_email.text!!.isBlank() || et_password.text!!.isBlank()){
                Toast.makeText(applicationContext,"Email and password should be valid.",Toast.LENGTH_LONG).show()
            }
            else {
                val sqliteManager = SQLiteManager(applicationContext)
                val isLoggedIn = sqliteManager.loginAuthentication(et_email.text.toString(), et_password.text.toString())
                if (isLoggedIn) {
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvHaventAccount.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
}