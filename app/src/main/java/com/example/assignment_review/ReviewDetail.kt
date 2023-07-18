package com.example.assignment_review

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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


        val reviewCheckManager = SQLiteManager(applicationContext)
        val userId = reviewCheckManager.getUserIDFromSession()

        // Query the REVIEWS table
        val query = "SELECT CONTENT, USERNAME FROM REVIEW WHERE ID = $reviewId"
        val cursor = db.rawQuery(query, null)
        var deleteReview = findViewById<Button>(R.id.delete_button)
        var editReview = findViewById<Button>(R.id.btn_edit)
        var btn_comment = findViewById<Button>(R.id.btn_comment)
        btn_comment.visibility = View.GONE
        val dislikeIcon = findViewById<ImageView>(R.id.dislike_icon)
        val like_icon = findViewById<ImageView>(R.id.like_icon)
        val color = Color.parseColor("#FF0000")
        val bluecolor = Color.parseColor("#0000FF")
        val blackcolor = Color.parseColor("#000000")
        if(reviewCheckManager.isReviewDisliked(userId, reviewId)){
            dislikeIcon.setColorFilter(color)
        }
        if(reviewCheckManager.isReviewLiked(userId, reviewId)){
            like_icon.setColorFilter(bluecolor)
        }
        dislikeIcon.setOnClickListener {
            dislikeIcon.setColorFilter(color)
            like_icon.setColorFilter(blackcolor)
            val sqliteManager = SQLiteManager(applicationContext)

            sqliteManager.toggleReviewAction(userId, reviewId, "dislike")
        }
        like_icon.setOnClickListener {
            like_icon.setColorFilter(bluecolor)
            dislikeIcon.setColorFilter(blackcolor)
            val sqliteManager = SQLiteManager(applicationContext)
            val userId = sqliteManager.getUserIDFromSession()
            sqliteManager.toggleReviewAction(userId, reviewId, "like")
        }

        editReview.setOnClickListener {
            val intent = Intent(this, EditReviewActivity::class.java)
            intent.putExtra("reviewId", reviewId)
            intent.putExtra("contentText", contentText.text)
            startActivity(intent)
        }


        if (cursor.moveToFirst()) {
            val usernameIndex = cursor.getColumnIndex("USERNAME")
            val contentIndex = cursor.getColumnIndex("CONTENT")
            val username = if (usernameIndex != -1) cursor.getString(usernameIndex) else ""
            val content = if (contentIndex != -1) cursor.getString(contentIndex) else ""
            contentText.text = content;
            usernameText.text = username;

            val sqliteManager = SQLiteManager(applicationContext)
            if(username != sqliteManager.getUserNameFromSession()){
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