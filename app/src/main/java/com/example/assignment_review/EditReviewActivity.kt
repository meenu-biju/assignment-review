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
        val review = findViewById<TextInputEditText>(R.id.submitReview)
        val contentText = intent.getStringExtra("contentText")
        val reviewBtn = findViewById<Button>(R.id.btn_submit)
        val reviewId = intent.getIntExtra("reviewId", 0)
        if (reviewId > 0){
            review.setText(contentText)
        }
        reviewBtn.setOnClickListener {

            val sqliteManager = SQLiteManager(applicationContext)

            if (reviewId > 0){
                sqliteManager.updateReview(reviewId, review.text.toString())
            }
            else {
                sqliteManager.insertReview(review.text.toString())
            }
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}