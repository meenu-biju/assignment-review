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
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.tvHaveAccount.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}