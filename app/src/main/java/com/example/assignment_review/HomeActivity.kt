package com.example.assignment_review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_review.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), ReviewAdapter.OnItemClickListener {

    private  lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.listAllReviews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        displayReviews()


        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.addUserReview.setOnClickListener {
            startActivity(Intent(this, EditReviewActivity::class.java))
        }

    }

    private fun displayReviews() {
        var helper = SQLiteManager(applicationContext)
        var db = helper.readableDatabase

        // Query the REVIEWS table
        val query = "SELECT * FROM REVIEW"
        val cursor = db.rawQuery(query, null)

        // Create an ArrayList to store the reviews
        val reviewsList = ArrayList<String>()

        // Iterate through the cursor and retrieve the reviews
        if (cursor.moveToFirst()) {
            do {
                val usernameIndex = cursor.getColumnIndex("USERNAME")
                val contentIndex = cursor.getColumnIndex("CONTENT")
                val username = if (usernameIndex != -1) cursor.getString(usernameIndex) else ""
                val content = if (contentIndex != -1) cursor.getString(contentIndex) else ""
                val review = "$username: $content"
                reviewsList.add(review)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        // Create the ReviewAdapter with the reviewsList and set it on the RecyclerView
        reviewAdapter = ReviewAdapter(reviewsList, this)
        recyclerView.adapter = reviewAdapter
    }

    override fun onItemClick(review: String) {
        Toast.makeText(this, "Clicked review: $review", Toast.LENGTH_SHORT).show()
    }
}