package com.example.assignment_review
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assignment_review.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            var et_fullname = findViewById<TextInputEditText>(R.id.et_fullname)
            var et_email = findViewById<TextInputEditText>(R.id.et_email)
            var et_username = findViewById<TextInputEditText>(R.id.et_username)
            var et_password = findViewById<TextInputEditText>(R.id.et_password)
            if(et_fullname.text!!.isBlank() || et_email.text!!.isBlank() || et_username.text!!.isBlank() || et_password.text!!.isBlank()){
                Toast.makeText(applicationContext,"Please fill all fields.",Toast.LENGTH_LONG).show()
            }
            else {
                val sqliteManager = SQLiteManager(applicationContext)
                sqliteManager.insertIntoRegisterTable(et_fullname.text.toString(), et_email.text.toString(), et_username.text.toString(), et_password.text.toString())
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        binding.tvHaveAccount.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}