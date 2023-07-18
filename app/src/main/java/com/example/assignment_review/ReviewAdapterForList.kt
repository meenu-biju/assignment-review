package com.example.assignment_review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val reviewsList: List<String>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ReviewViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(review: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.review_individuals, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewsList[position]
        holder.reviewTextView.text = review

        holder.itemView.setOnClickListener {
            listener.onItemClick(review)
        }
    }

    override fun getItemCount() = reviewsList.size
}