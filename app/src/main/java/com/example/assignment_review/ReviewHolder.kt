package com.example.assignment_review

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val reviewTextView: TextView = itemView.findViewById(R.id.reviewTextView)
}