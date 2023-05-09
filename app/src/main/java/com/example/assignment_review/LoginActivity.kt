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

            var database = Database()
            var username = findViewById<TextInputEditText>(R.id.et_email)
            var password = findViewById<TextInputEditText>(R.id.et_password)
            var userExist = database.authenticate(username.text.toString(), password.text.toString())
            if (userExist == true) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            else {
                Toast.makeText(this, "Authentication error....", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvHaventAccount.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
}