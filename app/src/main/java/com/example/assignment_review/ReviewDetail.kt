package com.example.assignment_review

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment_review.databinding.ActivityHomeBinding

class ReviewDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_detail_view)
        var helper = SQLiteManager(applicationContext)
        var db = helper.readableDatabase
        val reviewId = intent.getIntExtra("reviewId", 0)
        val usernameText = findViewById<TextView>(R.id.card_title)
        val contentText = findViewById<TextView>(R.id.card_content)
        // Query the REVIEWS table
        val query = "SELECT CONTENT, USERNAME FROM REVIEW WHERE ID = $reviewId"
        val cursor = db.rawQuery(query, null)

        var content: String? = null
        var username: String? = null

        if (cursor.moveToFirst()) {
            val usernameIndex = cursor.getColumnIndex("USERNAME")
            val contentIndex = cursor.getColumnIndex("CONTENT")
            val username = if (usernameIndex != -1) cursor.getString(usernameIndex) else ""
            val content = if (contentIndex != -1) cursor.getString(contentIndex) else ""
            contentText.text = content;
            usernameText.text = username;
        }
    }
}