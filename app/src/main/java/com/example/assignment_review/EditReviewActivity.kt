package com.example.assignment_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class EditReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_review)

        val reviewBtn = findViewById<Button>(R.id.btn_submit)
        reviewBtn.setOnClickListener {
            val review = findViewById<TextInputEditText>(R.id.submitReview)
            val sqliteManager = SQLiteManager(applicationContext)
            sqliteManager.insertReview(review.text.toString())
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}