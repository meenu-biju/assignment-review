package com.example.assignment_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment_review.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.addUserReview.setOnClickListener {
            startActivity(Intent(this, EditReviewActivity::class.java))
        }

        binding.btnEdit.setOnClickListener {
             startActivity(Intent(this,EditReviewActivity::class.java))

        }
        binding.btnComment.setOnClickListener {
            startActivity(Intent(this,comment_Activity::class.java))
        }

    }
}