package com.example.assignment_review

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
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
        var deleteReview = findViewById<Button>(R.id.delete_button)
        var editReview = findViewById<Button>(R.id.btn_edit)

        if (cursor.moveToFirst()) {
            val usernameIndex = cursor.getColumnIndex("USERNAME")
            val contentIndex = cursor.getColumnIndex("CONTENT")
            val username = if (usernameIndex != -1) cursor.getString(usernameIndex) else ""
            val content = if (contentIndex != -1) cursor.getString(contentIndex) else ""
            contentText.text = content;
            usernameText.text = username;

            val sqliteManager = SQLiteManager(applicationContext)
            if(username != sqliteManager.getUserNameFromSession()){
                print(">>>>>>")
                deleteReview.visibility = View.GONE
                editReview.visibility = View.GONE
            }
        }


        deleteReview.setOnClickListener{
            var sqliteManager = SQLiteManager(applicationContext)
            var sessionDb = sqliteManager.writableDatabase
            val query = "ID = ?"
            val selectionArgs = arrayOf(reviewId.toString())
            sessionDb.delete("REVIEW", query, selectionArgs)
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}